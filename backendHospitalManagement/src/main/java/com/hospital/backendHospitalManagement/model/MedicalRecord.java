package com.hospital.backendHospitalManagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long recordId;

    private Long appointmentId;
    private Long patientId;
    private Long doctorId;
    
    private String documentName;
    private String category;
    private String date;
    private String notes;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] fileData;

    private String attachedFileName;

    public MedicalRecord() {
    }

    public MedicalRecord(Long appointmentId, Long patientId, Long doctorId, String documentName, String category, String date, String notes) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.documentName = documentName;
        this.category = category;
        this.date = date;
        this.notes = notes;
    }

    public MedicalRecord(Long appointmentId, Long patientId, Long doctorId, String documentName, String category, String date, String notes, byte[] fileData, String attachedFileName) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.documentName = documentName;
        this.category = category;
        this.date = date;
        this.notes = notes;
        this.fileData = fileData;
        this.attachedFileName = attachedFileName;
    }
}
