package com.arshacodes.onboard.controller;

import com.arshacodes.onboard.entity.ImageData;
import com.arshacodes.onboard.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private StorageService service;

    @GetMapping("/students")
    public ResponseEntity<List<ImageData>> getAllStudents() {
        List<ImageData> students = service.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/download/{type}/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String type, @PathVariable Long id) {
        byte[] data = service.downloadFile(type, id);
        MediaType mediaType;
        switch (type) {
            case "photo":
                mediaType = MediaType.IMAGE_JPEG;
                break;
            case "tenthCertificate":
            case "twelfthCertificate":
            case "resume":
            case "marksheet":
                mediaType = MediaType.APPLICATION_PDF;
                break;
            default:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(mediaType)
                .body(data);
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<?> approveStudent(@PathVariable Long id) {
        boolean success = service.approveStudent(id);
        if (success) {
            return ResponseEntity.ok("Student approved and moved to selected_students.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found.");
        }
    }
}
