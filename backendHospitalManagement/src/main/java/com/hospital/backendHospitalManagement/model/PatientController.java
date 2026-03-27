package com.hospital.backendHospitalManagement.model;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PatientController {

    private final AppointmentRepo repository;

    public PatientController(AppointmentRepo repository) {
        this.repository = repository;
    }

    @PostMapping(value = "/{patientId}")
    public Appointment createAppointment(@RequestBody Appointment appointment) {
        return repository.save(appointment);
    }

    @GetMapping(value = "/{patiendId}")
    public List<Appointment> getAllAppointments(@PathVariable Long patientId){
        return repository.findByPatientId(patientId);
    }

}
