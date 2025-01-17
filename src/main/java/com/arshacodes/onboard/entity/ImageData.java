package com.arshacodes.onboard.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="ImageData")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ImageData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String name;
    private String dob;
    private String email;
    private String bdgrp;
    private String type;

    @Lob
    @Column(name="imagedata")
    private byte[] imageData;

    @Lob
    @Column(name = "tenth_certificate")
    private byte[] tenthCertificate;

    @Lob
    @Column(name = "twelfth_certificate")
    private byte[] twelfthCertificate;

    @Lob
    @Column(name = "resume")
    private byte[] resume;

    @Lob
    @Column(name="marksheet")
    private byte[] marksheet;

    @Column(name="status", nullable = true)
    private String status = "Pending";  // Default value is "Pending"


}

