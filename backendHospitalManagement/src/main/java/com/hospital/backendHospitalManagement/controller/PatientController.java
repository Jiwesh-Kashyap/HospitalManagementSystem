package com.hospital.backendHospitalManagement.controller;

import com.hospital.backendHospitalManagement.model.Appointment;
import com.hospital.backendHospitalManagement.model.AppointmentRepo;
import com.hospital.backendHospitalManagement.model.Doctor;
import com.hospital.backendHospitalManagement.model.DoctorRepo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class PatientController {
    private final AppointmentRepo appointmentRepo;
    private final DoctorRepo doctorRepo;
    private final com.hospital.backendHospitalManagement.model.PatientRepo patientRepo;

    private static final Map<String, String> healthConditionToSpecialisation = Map.of(
            "Dental Care", "Dentist",
            "Joint Pain", "Orthopaedic"
            // You can add more mappings here
    );

    public PatientController(AppointmentRepo appointmentRepo, DoctorRepo doctorRepo, com.hospital.backendHospitalManagement.model.PatientRepo patientRepo){
        this.appointmentRepo = appointmentRepo;
        this.doctorRepo = doctorRepo;
        this.patientRepo = patientRepo;
    }

    @GetMapping("/patient/{patientId}/appointments")
    public List<Appointment> getAllAppointmentsForAPatient(@PathVariable Long patientId){
        return appointmentRepo.findByPatientId(patientId);
    }

    @PostMapping("/patient/{patientId}/appointments")
    public Appointment addAppointment(@PathVariable Long patientId,
                                            @RequestParam("healthCondition") String healthCondition,
                                            @RequestParam("typeOfAppointment") String typeOfAppointment) {
        Appointment newAppointment = new Appointment();

        String specialisation = healthConditionToSpecialisation.getOrDefault(healthCondition, "General Physician");

        java.util.List<Doctor> doctorList = doctorRepo.findBySpecialisation(specialisation);
        if (doctorList.isEmpty()) {
            throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "No doctor available for this health condition!");
        }
        Doctor appointedDoctor = doctorList.get(new java.util.Random().nextInt(doctorList.size()));

        newAppointment.setDoctorId(appointedDoctor.getId());
        newAppointment.setPatientId(patientId);
        newAppointment.setTypeOfAppointment(typeOfAppointment);

        // Fetch patient and update health condition in db
        com.hospital.backendHospitalManagement.model.Patient patient = patientRepo.findById(patientId)
                .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Patient not found!"));
        patient.setHealthCondition(healthCondition);
        patientRepo.save(patient);

        return appointmentRepo.save(newAppointment);
    }
}
