package com.arshacodes.onboard.repository;

import com.arshacodes.onboard.entity.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StorageRepo extends JpaRepository<ImageData, Long> {

    Optional<ImageData> findByName(String fileName);

}
