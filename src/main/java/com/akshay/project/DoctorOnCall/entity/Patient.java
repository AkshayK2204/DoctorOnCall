package com.akshay.project.DoctorOnCall.entity;

import com.akshay.project.DoctorOnCall.enums.BLOOD_TYPE;
import com.akshay.project.DoctorOnCall.enums.GENDER;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "patients")
@NoArgsConstructor
public class Patient extends User {

    private String phoneNumber;
    private String address;
    private GENDER gender;

    @Lob
    private String medicalHistory;
    //private String currentMedications;
    //private String allergies;
    private BLOOD_TYPE bloodType;
    private String emergencyContact;
    private Double height;
    private Double weight;


    public String getFormattedPatientId() {
        return "P" + getId();
    }
}
