//package com.akshay.project.DoctorOnCall.entity;
//
//import jakarta.persistence.*;
//import lombok.Data;
//
//import java.time.LocalDateTime;
//
//@Data
//@Entity
//public class Appointment {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "patient_id")
//    private Patient patient;
//
//    @ManyToOne
//    @JoinColumn(name = "doctor_id")
//    private Doctor doctor;
//
//    private LocalDateTime appointmentDate;
//    private String appointmentType;
//
//    public String getFormattedAppointmentId() {
//        return "A" + id;
//    }
//}
