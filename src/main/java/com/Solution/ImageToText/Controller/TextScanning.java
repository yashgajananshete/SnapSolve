package com.Solution.ImageToText.Controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.sql.SQLException;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.Solution.ImageToText.service.ImageService;

import net.sourceforge.tess4j.Tesseract;

@Service
public class TextScanning {
	
	@Value("${tess4j.tessdata.path}")
    private String tessDataPath;
	
	@Autowired
	ImageService iService;
	public String Scan() {
		System.out.println(tessDataPath);
		String text="";
		String oneLineString=" ";
		String text1 ="below is the question give me only ans for that not explanation";
		byte[] getImage = null;
		Tesseract tr = new Tesseract();
		tr.setDatapath(tessDataPath);
		System.out.println("Datapath set");
		System.out.println("Starting Getting Image...");
		try {
			getImage = iService.getImageFromDatabase();
			System.out.println("successfull : "+ getImage);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Failed getting image");
			e.printStackTrace();
		}
		System.out.println("Image gettring process Stoped");
		try {
//			tr.setDatapath(tessDataPath);
			ByteArrayInputStream inputStream = new ByteArrayInputStream(getImage);
	        BufferedImage image = ImageIO.read(inputStream);
	        text = tr.doOCR(image);
	        System.out.println("Text From Databse: "+ text);
	        
//			text = tr.doOCR(new File(
//					"src\\main\\resources\\static\\images\\"+fileName));
//			System.out.println(text);
			oneLineString = text.replaceAll("\n", " ");
			System.out.println("TextScan:  "+ oneLineString);
			//text = "A train running at the speed of 60 km/hr crosses a pole in 9 seconds. What is the length of the train?";
		} catch (Exception e) {
			// TODO: handle exception
		}
		return oneLineString+""+text1;
	}

}
