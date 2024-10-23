package com.akshay.project.DoctorOnCall.service;

import com.akshay.project.DoctorOnCall.entity.Doctor;
import com.akshay.project.DoctorOnCall.repository.DoctorRepository;
import com.akshay.project.DoctorOnCall.repository.PatientRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DoctorService extends UserService {

    //@Autowired
    //private final DoctorRepository doctorRepository;


    public DoctorService(BCryptPasswordEncoder bCryptPasswordEncoder, DoctorRepository doctorRepository, PatientRepository patientRepository) {
        super(bCryptPasswordEncoder,doctorRepository,patientRepository);
        //this.doctorRepository = doctorRepository;
    }

    @Override
    public Optional<Doctor> findByEmail(String email) {
        return doctorRepository.findByEmail(email);
    }



    @Transactional
    public void registerDoctor(Doctor doctor) {
        //Doctor doctor = (Doctor) user;
        if (findByEmail(doctor.getEmail()).isPresent()) {
            throw new IllegalStateException("Email already registered...");
        }
        //doctor.setRole(ROLE.DOCTOR);
        registerUser(doctor, doctor.getPassword());
        doctorRepository.save(doctor);
    }
}
