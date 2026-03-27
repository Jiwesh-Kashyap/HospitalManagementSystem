package com.hospital.backendHospitalManagement.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;

@Entity
public class Doctor extends Person {

    private String specialisation;
    private List<Appointment> appointments;    //stores the ids of appointments

    public Doctor(Long id, String name, String role, String email, String specialisation, List<Appointment> appointments) {
        super(id, name, role, email);
        this.specialisation = specialisation;
        this.appointments = new ArrayList<>();
    }

    public String getSpecialisation() {
        return specialisation;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setSpecialisation(String s) {
        this.specialisation = s;
    }

    public void addAppointment(Appointment appointment){
        appointments.add(appointment);
    }
}
