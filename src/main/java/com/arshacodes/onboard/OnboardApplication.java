package com.arshacodes.onboard;

import com.arshacodes.onboard.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("/image")

public class OnboardApplication {
	@Autowired

	private StorageService service;

	@PostMapping
	public ResponseEntity<?> uploadImages(
			@RequestParam("images") List<MultipartFile> files,
			@RequestParam("name") String name,
			@RequestParam("email") String email,
			@RequestParam("dob") String dob,
			@RequestParam("bdgrp") String bdgrp) throws IOException {
		String uploadResult = service.uploadImages(files, name, email, dob, bdgrp);
		return ResponseEntity.status(HttpStatus.OK)
				.body(uploadResult);
	}
	@GetMapping("/{fileName}")

	public ResponseEntity<?> downloadImage(@PathVariable String fileName){
		byte[] imageData=service.downloadImage(fileName);
		return ResponseEntity.status(HttpStatus.OK)
				.contentType(MediaType.valueOf("image/png"))
				.body(imageData);

	}
	public static void main(String[] args) {
		SpringApplication.run(OnboardApplication.class, args);
		System.out.println("Hello");
	}

}
