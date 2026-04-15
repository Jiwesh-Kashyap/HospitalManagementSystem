package com.hospital.backendHospitalManagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    private Long patientId;
    private Long doctorId;
    private Long appointmentId;
    private String invoiceNumber;
    private String service;
    private Double amount;
    private String date;

    public Bill() {}

    public Bill(Long patientId, Long doctorId, Long appointmentId, String invoiceNumber, String service, Double amount, String date) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentId = appointmentId;
        this.invoiceNumber = invoiceNumber;
        this.service = service;
        this.amount = amount;
        this.date = date;
    }
}
