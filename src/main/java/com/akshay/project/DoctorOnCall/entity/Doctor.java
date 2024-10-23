package com.akshay.project.DoctorOnCall.entity;

import com.akshay.project.DoctorOnCall.enums.ROLE;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "doctors")
public class Doctor extends User {

    private String phoneNumber;
    private String specialization;

    @Lob
    private String biography;
    private Double consultationFee;
    private String availability;

    public Doctor() {
        this.setRole(ROLE.DOCTOR);
    }

    public String getFormattedDoctorId() {
        return "D" + getId();
    }
}
