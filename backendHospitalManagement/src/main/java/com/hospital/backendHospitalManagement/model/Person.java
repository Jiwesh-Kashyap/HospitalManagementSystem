package com.hospital.backendHospitalManagement.model;

import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Person{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;
    private String name;
    private String role;
    @Column(unique = true)
    private String email;
    private String password;

    public Person() {
    }

    public Person(Long id, String name, String role, String email, String password) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.email = email;
        this.password = password;
    }
}
