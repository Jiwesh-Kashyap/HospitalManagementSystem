package com.hospital.backendHospitalManagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String role;
    @Column(unique = true)
    private String email;

    public Person(Long id, String name, String role, String email) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.email = email;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getEmail(){
        return email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String n) {
        name = n;
    }

    public void setRole(String s) {
        role = s;
    }

    public void setEmail(String s){
        email = s;
    }
}
