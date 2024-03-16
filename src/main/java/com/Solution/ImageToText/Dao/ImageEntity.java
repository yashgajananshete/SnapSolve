package com.Solution.ImageToText.Dao;


import java.sql.Blob;
import java.sql.SQLException;

//import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

//import com.mysql.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "images")
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob // Indicates that this field should be persisted as a large object (BLOB/CLOB) in the database
    @Column(name = "image_data", nullable = false)
    private Blob imageData;

    // Constructors, getters, and setters
    // Omitted for brevity

    // Getter and setter for id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter and setter for imageData
    public Blob getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
    	Blob blobImage = null;
		try {
			blobImage = new javax.sql.rowset.serial.SerialBlob(imageData);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			e.printStackTrace();
		}
        this.imageData = (Blob) blobImage;
    }
}


//public String addlmagePost(HttpservtetRequest MultipartFite file) throws
//byte[l bytes file.getBytes();
//Blob bt0b = new javax. sqt. rowset. serial. SerialBlob(bytes);
//Image image new Image();
//image. setlmage(bt0b) ;
//imageservice . create(image) ;
