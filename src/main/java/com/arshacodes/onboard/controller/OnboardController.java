package com.arshacodes.onboard.controller;

import com.arshacodes.onboard.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/image")
public class OnboardController {

    @Autowired
    private StorageService service;

    @PostMapping
    public ResponseEntity<?> uploadImages(
            @RequestParam("images") List<MultipartFile> files,
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("dob") String dob,
            @RequestParam("bdgrp") String bdgrp,
            @RequestParam("tenthCertificate") MultipartFile tenthCertificate,
            @RequestParam("twelfthCertificate") MultipartFile twelfthCertificate,
            @RequestParam("resume") MultipartFile resume,
            @RequestParam("marksheet") MultipartFile marksheet) throws IOException {
        String uploadResult = service.uploadImages(files, name, email, dob, bdgrp, tenthCertificate, twelfthCertificate, resume, marksheet);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadResult);
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable String fileName){
        byte[] imageData = service.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }

    @GetMapping("/tenthCertificate/{id}")
    public ResponseEntity<?> downloadTenthCertificate(@PathVariable Long id) {
        byte[] data = service.downloadTenthCertificate(id);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_PDF)
                .body(data);
    }

    @GetMapping("/twelfthCertificate/{id}")
    public ResponseEntity<?> downloadTwelfthCertificate(@PathVariable Long id) {
        byte[] data = service.downloadTwelfthCertificate(id);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_PDF)
                .body(data);
    }

    @GetMapping("/resume/{id}")
    public ResponseEntity<?> downloadResume(@PathVariable Long id) {
        byte[] data = service.downloadResume(id);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_PDF)
                .body(data);
    }

    @GetMapping("/marksheet/{id}")
    public ResponseEntity<?> downloadMarksheet(@PathVariable Long id) {
        byte[] data = service.downloadMarksheet(id);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_PDF)
                .body(data);
    }
}
