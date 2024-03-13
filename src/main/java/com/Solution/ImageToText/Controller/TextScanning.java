package com.Solution.ImageToText.Controller;

import java.io.File;

import org.springframework.stereotype.Service;

import net.sourceforge.tess4j.Tesseract;

@Service
public class TextScanning {
	public String Scan(String fileName) {
		String text="";
		String text1 ="below is the question give me only ans for that not explanation";
		Tesseract tr = new Tesseract();
		try {
			tr.setDatapath("Tess4J\\tessdata");
			text = tr.doOCR(new File(
					"src\\main\\resources\\static\\images\\"+fileName));
			System.out.println(text);
			String oneLineString = text.replaceAll("\n", " ");
			System.out.println(oneLineString);
			//text = "A train running at the speed of 60 km/hr crosses a pole in 9 seconds. What is the length of the train?";
		} catch (Exception e) {
			// TODO: handle exception
		}
		return text+""+text1;
	}

}
