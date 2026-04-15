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
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long appointmentId;

    private Long doctorId;
    private Long patientId;
    private String typeOfAppointment;
    private String status;
    private String notes;

    public Appointment() {
    }

    public Appointment(Long appointmentId, Long doctorId, Long patientId, String type) {
        this.appointmentId = appointmentId;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.typeOfAppointment = type;
        this.status = "Pending";
        this.notes = "";
    }

}
