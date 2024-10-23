package com.akshay.project.DoctorOnCall.service;

import com.akshay.project.DoctorOnCall.entity.Patient;
import com.akshay.project.DoctorOnCall.repository.DoctorRepository;
import com.akshay.project.DoctorOnCall.repository.PatientRepository;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

    @Service
    @Primary
    public class PatientService extends UserService {

        //@Autowired
        //private final PatientRepository patientRepository;

        public PatientService(BCryptPasswordEncoder passwordEncoder, DoctorRepository doctorRepository, PatientRepository patientRepository) {
            super(passwordEncoder,doctorRepository,patientRepository);
            //this.patientRepository = patientRepository;
        }

        @Override
        public Optional<Patient> findByEmail(String email) {
            return patientRepository.findByEmail(email);
        }

        @Transactional
        public void registerPatient(Patient patient) {
            //Patient patient = (Patient) user;
            if (findByEmail(patient.getEmail()).isPresent()) {
                throw new IllegalStateException("Email already registered...");
            }

            //patient.setRole(ROLE.PATIENT);
            registerUser(patient, patient.getPassword());
            patientRepository.save(patient);
        }


    }


