package com.Solution.ImageToText.Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

import org.json.JSONObject;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.Solution.ImageToText.Dao.ImageToTextRepo;
import com.Solution.ImageToText.service.ImageService;

import org.springframework.util.StringUtils;

@Controller
public class MainController {

	@Autowired
	TextScanning scanning;

	@Autowired
	ChatClient chatClient;
	@Value("${OPENAI_API_KEY}")
	String apiKey;
	
	@Autowired
	ImageService iService;
	
	Prompt prompt;
	
	@RequestMapping("/")
	public String index() {
		System.out.println(apiKey);
		return "index";
	}
	
	 @PostMapping("/upload")
	    public String handleFileUpload(@RequestParam("image") MultipartFile file, Model model) throws IOException {
	        // Check if the file is empty
		 
		 System.out.println(file.getContentType());
		 System.out.println("hello");
	        if (file.isEmpty()) {
	            return "index"; // or redirect to the index page
	        }

	        	String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		        System.out.println(fileName);
		        
		     iService.saveImageToDatabase(file);
		     try {
				byte[] getImage = iService.getImageFromDatabase();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		     // Define the directory on the "D" drive where you want to save the files
	        String uploadDir = "src\\main\\resources\\static\\images";

	        try {
	            // Resolve the path for the new file
	            Path filePath = Paths.get(uploadDir, fileName);

	            // Save the file to the specified location
	            Files.copy(file.getInputStream(), filePath);
	            String Text = scanning.Scan(fileName);
	            System.out.println("Text From main Controller: " +  Text);
	            System.out.println("fileName:  "+fileName);
//	            String Text = scanning.Scan("question1");
	    		ChatResponse response = chatClient.call(new Prompt(Text,OpenAiChatOptions.builder().withModel("gpt-3.5-turbo-0125").withTemperature((float) 0.4).build()));
	    		JSONObject jsonObject = new JSONObject(response);
	    		System.out.println("response"+ response);
	    		// Extract content from output object
	    		JSONObject resultObject = jsonObject.getJSONObject("result");
	    		JSONObject outputObject = resultObject.getJSONObject("output");
	    		String content = outputObject.getString("content");

	    		// Print the extracted content
	    		System.out.println("Extracted content:");
	    		System.out.println(content);
	            
	    		model.addAttribute("answer", content);
	    		
	    		
	    		// deletging file
	    		File file2 = new File("src\\\\main\\\\resources\\\\static\\\\images\\"+fileName);
	    		iService.deleteAllImages();
	            // Check if the file exist
	            if (file2.exists()) {
	                // Attempt to delete the file
	                if (file2.delete()) {
	                    System.out.println("File deleted successfully.");
	                } else {
	                    System.out.println("Failed to delete the file.");
	                }
	            } else {
	                System.out.println("File does not exist.");
	                
	            }

	            // Return a success response or redirect to a different page
	            return "response"; // or redirect to a different page
	        } catch (IOException e) {
	            // Handle the exception (e.g., log error message)
	            e.printStackTrace();
	            return "index"; // or redirect to the index page
	        }
	        
	        
	    }
}
