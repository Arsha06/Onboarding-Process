package com.arshacodes.onboard.service;

import com.arshacodes.onboard.entity.ImageData;
import com.arshacodes.onboard.entity.SelectedStudent;
import com.arshacodes.onboard.repository.StorageRepo;
import com.arshacodes.onboard.repository.SelectedStudentRepo;
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

    @Autowired
    private SelectedStudentRepo selectedStudentRepo;

    public String uploadImages(List<MultipartFile> files, String userName, String userEmail, String userDob, String userBdgrp, MultipartFile tenthCertificate, MultipartFile twelfthCertificate, MultipartFile resume, MultipartFile marksheet) throws IOException {
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
                    .tenthCertificate(tenthCertificate.getBytes())
                    .twelfthCertificate(twelfthCertificate.getBytes())
                    .resume(resume.getBytes())
                    .marksheet(marksheet.getBytes())
                    .status("Pending")
                    .build());
            if (imageData != null) {
                result.append("File uploaded successfully: ").append(file.getOriginalFilename()).append("\n");
            }
        }
        return result.toString();
    }

    public byte[] downloadImage(String fileName) {
        Optional<ImageData> dbImageData = repository.findByName(fileName);
        return dbImageData.map(data -> ImageUtils.decompressImage(data.getImageData())).orElse(null);
    }

    public byte[] downloadTenthCertificate(Long id) {
        Optional<ImageData> dbImageData = repository.findById(id);
        return dbImageData.map(ImageData::getTenthCertificate).orElse(null);
    }

    public byte[] downloadTwelfthCertificate(Long id) {
        Optional<ImageData> dbImageData = repository.findById(id);
        return dbImageData.map(ImageData::getTwelfthCertificate).orElse(null);
    }

    public byte[] downloadResume(Long id) {
        Optional<ImageData> dbImageData = repository.findById(id);
        return dbImageData.map(ImageData::getResume).orElse(null);
    }

    public byte[] downloadMarksheet(Long id) {
        Optional<ImageData> dbImageData = repository.findById(id);
        return dbImageData.map(ImageData::getMarksheet).orElse(null);
    }

    // New methods for admin functionalities
    public List<ImageData> getAllStudents() {
        return repository.findAll();
    }

    public byte[] downloadFile(String type, Long id) {
        Optional<ImageData> dbImageData = repository.findById(id);
        if (dbImageData.isPresent()) {
            ImageData imageData = dbImageData.get();
            switch (type) {
                case "photo":
                    return ImageUtils.decompressImage(imageData.getImageData());
                case "tenthCertificate":
                    return imageData.getTenthCertificate();
                case "twelfthCertificate":
                    return imageData.getTwelfthCertificate();
                case "resume":
                    return imageData.getResume();
                case "marksheet":
                    return imageData.getMarksheet();
                default:
                    return null;
            }
        }
        return null;
    }

    public boolean approveStudent(Long id) {
        Optional<ImageData> optionalImageData = repository.findById(id);
        if (optionalImageData.isPresent()) {
            ImageData imageData = optionalImageData.get();
            imageData.setStatus("Approved");
            repository.save(imageData);

            SelectedStudent selectedStudent = new SelectedStudent();
            selectedStudent.setName(imageData.getName());
            selectedStudent.setDob(imageData.getDob());
            selectedStudent.setEmail(imageData.getEmail());
            selectedStudent.setBdgrp(imageData.getBdgrp());
            selectedStudent.setPhoto(imageData.getImageData());
            selectedStudent.setTenthCertificate(imageData.getTenthCertificate());
            selectedStudent.setTwelfthCertificate(imageData.getTwelfthCertificate());
            selectedStudent.setResume(imageData.getResume());
            selectedStudent.setMarksheet(imageData.getMarksheet());
            selectedStudentRepo.save(selectedStudent);

            return true;
        }
        return false;
    }

    public ImageData updateStatus(Long id, String newStatus) {
        Optional<ImageData> optionalImageData = repository.findById(id);
        if (optionalImageData.isPresent()) {
            ImageData imageData = optionalImageData.get();
            imageData.setStatus(newStatus);
            return repository.save(imageData);
        }
        return null;
    }
}
