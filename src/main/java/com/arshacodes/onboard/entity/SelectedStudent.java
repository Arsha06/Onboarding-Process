package com.arshacodes.onboard.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="selected_students")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SelectedStudent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String dob;
    private String email;
    private String bdgrp;

    @Lob
    @Column(name="tenth_certificate")
    private byte[] tenthCertificate;

    @Lob
    @Column(name="twelfth_certificate")
    private byte[] twelfthCertificate;

    @Lob
    @Column(name="resume")
    private byte[] resume;

    @Lob
    @Column(name="marksheet")
    private byte[] marksheet;

    @Lob
    @Column(name="photo")
    private byte[] photo;
}
