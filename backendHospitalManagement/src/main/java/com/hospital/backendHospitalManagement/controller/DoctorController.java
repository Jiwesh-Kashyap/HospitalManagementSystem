package com.hospital.backendHospitalManagement.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.backendHospitalManagement.model.Appointment;
import com.hospital.backendHospitalManagement.model.AppointmentRepo;

@RestController
public class DoctorController {

    private final AppointmentRepo repository;

    public DoctorController(AppointmentRepo repository) {
        this.repository = repository;
    }


    @GetMapping(value = "/{doctorId}/appointments")
    public List<Appointment> getAllAppointments(@PathVariable Long doctorId) {
        List<Appointment> list = repository.findByDoctorId(doctorId);

        return list;
    }

    @PostMapping(value = "/{doctorId}/appointments")
    public List<Appointment> updateStatusOfAppointment(@PathVariable Long doctorId,
            @RequestParam("appointment_id") Long appointmentId) {

        Appointment appointment = repository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found!!"));

        appointment.setStatus(true);
        repository.save(appointment);

        return repository.findByDoctorId(doctorId);
    }
}
