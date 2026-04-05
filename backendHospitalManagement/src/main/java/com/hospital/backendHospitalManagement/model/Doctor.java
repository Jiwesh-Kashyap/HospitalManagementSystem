package com.hospital.backendHospitalManagement.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;

@Getter
@Setter
@Entity
public class Doctor extends Person {

    private String specialisation;
    @Transient
    private List<Appointment> appointments;    //stores the ids of appointments

    public Doctor() {
        this.appointments = new ArrayList<>();
    }

    public Doctor(Long id, String name, String email, String password, String specialisation, List<Appointment> appointments) {
        super(id, name, "doctor", email, password);
        this.specialisation = specialisation;
        this.appointments = new ArrayList<>();
    }

    public void addAppointment(Appointment appointment){
        appointments.add(appointment);
    }
}
