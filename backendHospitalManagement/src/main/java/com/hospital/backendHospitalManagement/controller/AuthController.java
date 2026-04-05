package com.hospital.backendHospitalManagement.controller;

import com.hospital.backendHospitalManagement.config.JwtService;
import com.hospital.backendHospitalManagement.dto.AuthRequest;
import com.hospital.backendHospitalManagement.dto.SignupRequest;
import com.hospital.backendHospitalManagement.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtService jwtService;
    private final PersonRepo personRepo;
    private final PatientRepo patientRepo;
    private final DoctorRepo doctorRepo;
    private final PasswordEncoder passwordEncoder;

    public AuthController(JwtService jwtService, PersonRepo personRepo, PatientRepo patientRepo, DoctorRepo doctorRepo, PasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.personRepo = personRepo;
        this.patientRepo = patientRepo;
        this.doctorRepo = doctorRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(@RequestBody SignupRequest request) {
        if (personRepo.findByEmail(request.email()).isPresent()) {
            throw new ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST, "Email already in use");
        }

        String hashedPassword = passwordEncoder.encode(request.password());
        String token = null;


        if (request.role().equalsIgnoreCase("DOCTOR")) {

            Doctor newDoctor = new Doctor();

            newDoctor.setName(request.name());
            newDoctor.setEmail(request.email());
            newDoctor.setPassword(hashedPassword);
            newDoctor.setRole("doctor");

            newDoctor.setSpecialisation(request.specialisation());

            token = jwtService.generateToken(newDoctor.getEmail());

            doctorRepo.save(newDoctor);

        } else if (request.role().equalsIgnoreCase("PATIENT")) {

            Patient newPatient = new Patient();

            newPatient.setName(request.name());
            newPatient.setEmail(request.email());
            newPatient.setPassword(hashedPassword);
            newPatient.setRole("patient");

            newPatient.setBloodGroup(request.bloodGroup());

            token = jwtService.generateToken(newPatient.getEmail());

            patientRepo.save(newPatient);

        } else {
            return ResponseEntity.badRequest().build();
        }

        Map<String, String> response = new HashMap<>();
        response.put("token", token);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthRequest request){
        Person person = personRepo.findByEmail(request.username())
                .orElseThrow(() -> new ResponseStatusException(UNAUTHORIZED, "Invalid credentials"));

        if (!passwordMatches(request.password(), person.getPassword())) {
            throw new ResponseStatusException(UNAUTHORIZED, "Invalid credentials");
        }

        String token = jwtService.generateToken(person.getEmail());

        Map<String, String> response = new HashMap<>();
        response.put("token", token);

        return ResponseEntity.ok(response);
    }

    private boolean passwordMatches(String rawPassword, String storedPassword) {
        if (storedPassword == null || rawPassword == null) {
            return false;
        }

        if (passwordEncoder.matches(rawPassword, storedPassword)) {
            return true;
        }

        return storedPassword.equals(rawPassword);
    }
}
