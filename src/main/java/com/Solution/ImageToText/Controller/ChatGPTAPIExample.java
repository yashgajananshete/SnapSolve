package com.Solution.ImageToText.Controller;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Component;

@Component
public class ChatGPTAPIExample {

   public String chatGPT(String prompt) {
	   System.out.println(prompt);
       String url = "https://api.openai.com/v1/chat/completions";
//       String apiKey = "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";
       String apiKey = "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";
       String model = "gpt-3.5-turbo";

       try {
           @SuppressWarnings("deprecation")
		URL obj = new URL(url);
           HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
           connection.setRequestMethod("POST");
           connection.setRequestProperty("Authorization", "Bearer " + apiKey);
           connection.setRequestProperty("Content-Type", "application/json");

           // The request body
           String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + prompt + "\"}]}";
           connection.setDoOutput(true);
           OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
           writer.write(body);
           writer.flush();
           writer.close();
           System.out.println(body);
           System.out.println("test");
           // Response from ChatGPT
           BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
           String line;
           System.out.println("test2");

           StringBuffer response = new StringBuffer();

           while ((line = br.readLine()) != null) {
               response.append(line);
               System.out.println(line);
           }
           System.out.println(response);
           br.close();
           System.out.println("test3");

           // calls the method to extract the message.
           return extractMessageFromJSONResponse(response.toString());

       } catch (IOException e) {
           throw new RuntimeException(e);
       }
   }

   public static String extractMessageFromJSONResponse(String response) {
       int start = response.indexOf("content")+ 11;

       int end = response.indexOf("\"", start);
       
       return response.substring(start, end);
   }
}
