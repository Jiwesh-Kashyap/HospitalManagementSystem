package com.hospital.backendHospitalManagement.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Patient extends Person {

    private String healthCondition;
    private String bloodGroup;
    private float weightInKgs;
    private float heightInCms;


    public Patient() {
    }

    public Patient(Long id, String name, String email, String password, String healthCondition) {
        super(id, name, "patient", email, password);
        this.healthCondition = healthCondition;
    }

    public Patient(Long id, String name, String email, String password, String healthCondition, String bloodGroup) {
        super(id, name, "patient", email, password);
        this.healthCondition = healthCondition;
        this.bloodGroup = bloodGroup;
    }

    public Patient(Long id, String name,String email, String password, String healthCondition, String bloodGroup, float weightInKgs) {
        super(id, name, "patient", email, password);
        this.healthCondition = healthCondition;
        this.bloodGroup = bloodGroup;
        this.weightInKgs = weightInKgs;
    }

    public Patient(Long id, String name,String email, String password, String healthCondition, String bloodGroup, float weightInKgs, float heightInCms) {
        super(id, name, "patient", email, password);
        this.healthCondition = healthCondition;
        this.bloodGroup = bloodGroup;
        this.weightInKgs = weightInKgs;
        this.heightInCms = heightInCms;
    }
}
