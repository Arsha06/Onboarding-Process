package com.arshacodes.onboard.service;

import com.arshacodes.onboard.entity.ImageData;
import com.arshacodes.onboard.repository.StorageRepo;
import com.arshacodes.onboard.util.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class StorageService {
    @Autowired
    private StorageRepo repository;

    public String uploadImages(List<MultipartFile> files, String userName, String userEmail, String userDob, String userBdgrp) throws IOException {
        StringBuilder result = new StringBuilder();

        for (MultipartFile file : files) {
            ImageData imageData = repository.save(ImageData.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .imageData(ImageUtils.compressImage(file.getBytes()))
                    .name(userName)
                    .email(userEmail)
                            .dob(userDob)
                            .bdgrp(userBdgrp)
                    .build());
            if (imageData != null) {
                result.append("File uploaded successfully: ").append(file.getOriginalFilename()).append("\n");
            }
        }
        return result.toString();
    }
    public byte[] downloadImage(String fileName){
        Optional<ImageData> dbImageData = repository.findByName(fileName);
        byte[] images=ImageUtils.decompressImage(dbImageData.get().getImageData());
        return images;
    }

}
