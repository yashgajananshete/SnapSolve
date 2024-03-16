package com.Solution.ImageToText.service;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Solution.ImageToText.Dao.ImageEntity;
import com.Solution.ImageToText.Dao.ImageToTextRepo;

@Service
public class ImageService {

    @Autowired
    private ImageToTextRepo imageRepository;

    public void saveImageToDatabase(MultipartFile file) {
        // Convert MultipartFile to byte array
        byte[] imageData = null;
		try {
			imageData = file.getBytes();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Exception From Service Class: "+e);
			e.printStackTrace();
		}
        System.out.println(imageData.length);
        // Save byte array to database
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setImageData(imageData); // Assuming imageData is the database column for storing the image
        imageRepository.save(imageEntity);
        System.out.println("File Saved Successfully");
    }
    
    public byte[] getImageFromDatabase() throws SQLException {
        List<ImageEntity> images = (List<ImageEntity>) imageRepository.findAll();
        if (!images.isEmpty()) {
            ImageEntity latestImage = images.get(images.size() - 1);
            Blob imageData = latestImage.getImageData();
            System.out.println("File Get Successfully");
            return imageData.getBytes(1, (int) imageData.length());
            
        }
        return null;
    }
    
    public void deleteAllImages() {
        imageRepository.deleteAll();
    }
}
