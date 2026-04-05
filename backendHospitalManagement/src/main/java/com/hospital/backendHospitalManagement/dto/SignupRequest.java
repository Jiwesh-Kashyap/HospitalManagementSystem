package com.hospital.backendHospitalManagement.dto;

public record SignupRequest(String name, String email, String password, String role, String bloodGroup, String specialisation) {
}
