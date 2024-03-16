package com.Solution.ImageToText.Dao;

//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ImageToTextRepo extends CrudRepository<ImageEntity, Long> {
    // Define any additional methods for custom queries if needed
}

