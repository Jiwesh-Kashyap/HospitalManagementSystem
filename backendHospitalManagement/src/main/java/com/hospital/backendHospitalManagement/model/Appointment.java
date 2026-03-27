package com.hospital.backendHospitalManagement.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appointmentId;
    private Long doctorId;
    private Long patientId;
    private String typeOfAppointment;
    private boolean status;

    public Appointment(Long appointmentId, Long doctorId, Long patientId, String type) {
        this.appointmentId = appointmentId;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.typeOfAppointment = type;
        status = false;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public String getTypeOfAppointment() {
        return typeOfAppointment;
    }

    public boolean getStatus() {
        return status;
    }

    public void setAppointmentId(Long id) {
        appointmentId = id;
    }

    public void setDoctorId(Long id) {
        this.doctorId = id;
    }

    public void setPatientId(Long id) {
        this.patientId = id;
    }

    public void setTypeOfAppointment(String s) {
        this.typeOfAppointment = s;
    }

    public void setStatus(boolean val) {
        status = val;
    }
}
