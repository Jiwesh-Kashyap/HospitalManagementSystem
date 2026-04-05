package com.hospital.backendHospitalManagement.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepo extends JpaRepository<Doctor, Long> {
    List<Doctor> findBySpecialisation(String specialisation);
}
