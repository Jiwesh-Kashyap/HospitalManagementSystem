package com.hospital.backendHospitalManagement;

import javax.sql.DataSource;

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
    CommandLineRunner initDatabase(PersonRepo personRepo, PasswordEncoder passwordEncoder, DataSource dataSource) {
		return args -> {
			try (var connection = dataSource.getConnection()) {
				System.out.println("Datasource URL: " + connection.getMetaData().getURL());
				System.out.println("Datasource Driver: " + connection.getMetaData().getDriverName());
			}

			if (personRepo.findByEmail("admin@hms.local").isEmpty()) {
				Person admin = new Person();
				admin.setName("admin");
				admin.setEmail("admin@hms.local");
//				admin.setHealthCondition("N/A");
				admin.setPassword(passwordEncoder.encode("password123"));
				personRepo.save(admin);
				System.out.println("Dummy user created: admin@hms.local");
			}
		};
	}

}
