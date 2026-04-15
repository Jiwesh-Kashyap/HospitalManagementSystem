package com.hospital.backendHospitalManagement;

import javax.sql.DataSource;

import com.hospital.backendHospitalManagement.model.Doctor;
import com.hospital.backendHospitalManagement.model.DoctorRepo;
import com.hospital.backendHospitalManagement.model.Person;
import com.hospital.backendHospitalManagement.model.PersonRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.awt.*;

@SpringBootApplication
public class BackendHospitalManagementApplication {

	public static void main(String[] args) {
		var context = SpringApplication.run(BackendHospitalManagementApplication.class, args);
		// Launch Swing UI
		System.setProperty("java.awt.headless", "false");
		if (!GraphicsEnvironment.isHeadless()) {
			com.hospital.backendHospitalManagement.ui.PromethiusFrame frame = new com.hospital.backendHospitalManagement.ui.PromethiusFrame(context);
			frame.launch();
		}
	}
	@Bean
    CommandLineRunner initDatabase(PersonRepo personRepo, DoctorRepo doctorRepo, PasswordEncoder passwordEncoder, DataSource dataSource) {
		return args -> {
			try (var connection = dataSource.getConnection()) {
				System.out.println("Datasource URL: " + connection.getMetaData().getURL());
				System.out.println("Datasource Driver: " + connection.getMetaData().getDriverName());
			}

			if (personRepo.findByEmail("admin@hms.local").isEmpty()) {
				Person admin = new Person();
				admin.setName("admin");
				admin.setEmail("admin@hms.local");
				admin.setPassword(passwordEncoder.encode("password123"));
				personRepo.save(admin);
				System.out.println("Dummy user created: admin@hms.local");
			}
            
            // Seed Doctor 1: Dr. Chaithra H
            if (personRepo.findByEmail("chaithra@apollo.com").isEmpty()) {
                Doctor d1 = new Doctor();
                d1.setName("Chaithra H");
                d1.setEmail("chaithra@apollo.com");
                d1.setPassword(passwordEncoder.encode("doctor123"));
                d1.setRole("doctor");
                d1.setSpecialisation("Internal Medicine Specialist");
                doctorRepo.save(d1);
                System.out.println("Seeded Doctor: chaithra@apollo.com | Pwd: doctor123");
            }

            // Seed Doctor 2: Dr. Summaiya Banu
            if (personRepo.findByEmail("summaiya@apollo.com").isEmpty()) {
                Doctor d2 = new Doctor();
                d2.setName("Summaiya Banu");
                d2.setEmail("summaiya@apollo.com");
                d2.setPassword(passwordEncoder.encode("doctor123"));
                d2.setRole("doctor");
                d2.setSpecialisation("General Practitioner");
                doctorRepo.save(d2);
                System.out.println("Seeded Doctor: summaiya@apollo.com | Pwd: doctor123");
            }

            // Seed Doctor 3: Dr. Syed Ismail Ali
            if (personRepo.findByEmail("syed@apollo.com").isEmpty()) {
                Doctor d3 = new Doctor();
                d3.setName("Syed Ismail Ali");
                d3.setEmail("syed@apollo.com");
                d3.setPassword(passwordEncoder.encode("doctor123"));
                d3.setRole("doctor");
                d3.setSpecialisation("General Physician");
                doctorRepo.save(d3);
                System.out.println("Seeded Doctor: syed@apollo.com | Pwd: doctor123");
            }

            // Seed Doctors for all major specializations
            String[] missingSpecialties = {
                "Neurology", "Pediatrics", "Cardiology", "Orthopedics", "Diabetes", 
                "Gastroenterology", "Dental", "Ophthalmology", "Dermatology", "Geriatrics"
            };

            for (String spec : missingSpecialties) {
                String docEmail = spec.toLowerCase() + "_doc@apollo.com";
                if (personRepo.findByEmail(docEmail).isEmpty()) {
                    Doctor d = new Doctor();
                    d.setName("Dr. " + spec + " Specialist");
                    d.setEmail(docEmail);
                    d.setPassword(passwordEncoder.encode("doctor123"));
                    d.setRole("doctor");
                    d.setSpecialisation(spec);
                    doctorRepo.save(d);
                    System.out.println("Seeded Doctor: " + docEmail + " | Spec: " + spec);
                }
            }
		};
	}

}
