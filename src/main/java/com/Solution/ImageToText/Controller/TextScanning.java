
package com.Solution.ImageToText.Controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.Solution.ImageToText.service.ImageService;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

@Service
public class TextScanning {
    
    @Value("${tess4j.tessdata.path}")
    private String tessDataPath;
    
    @Autowired
    ImageService iService;

    public String Scan() {
        Tesseract tesseract = new Tesseract();
        
        // Set the tessdata path from the environment variable
        tesseract.setDatapath(System.getenv("TESSDATA_PREFIX"));
        
        byte[] getImage = null;
        String oneLineString = " ";
        String text1 = "below is the question give me only ans for that not explanation";
        
        System.out.println("Starting Getting Image...");
        try {
            getImage = iService.getImageFromDatabase();
            System.out.println("Successful: " + getImage);
            
            ByteArrayInputStream inputStream = new ByteArrayInputStream(getImage);
            BufferedImage image = ImageIO.read(inputStream);
            String text = tesseract.doOCR(image);
            System.out.println("Text From Database: " + text);
            
            oneLineString = text.replaceAll("\n", " ");
            System.out.println("TextScan:  " + oneLineString);
            
        } catch (SQLException e) {
            System.out.println("Failed getting image");
            e.printStackTrace();
        } catch (TesseractException e) {
            System.out.println("Tesseract Exception:");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IO Exception:");
            e.printStackTrace();
        }
        System.out.println(oneLineString);
		if(oneLineString != " ")
			return oneLineString+""+text1;
		else
			return "write a msg for user that there is some problem occurs";
	
       
    }
}
