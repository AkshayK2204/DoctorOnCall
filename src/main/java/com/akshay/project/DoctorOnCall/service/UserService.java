package com.akshay.project.DoctorOnCall.service;

import com.akshay.project.DoctorOnCall.entity.Doctor;
import com.akshay.project.DoctorOnCall.entity.Patient;
import com.akshay.project.DoctorOnCall.entity.User;
import com.akshay.project.DoctorOnCall.repository.DoctorRepository;
import com.akshay.project.DoctorOnCall.repository.PatientRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

public abstract class UserService {
    protected final BCryptPasswordEncoder bCryptPasswordEncoder;
    protected final DoctorRepository doctorRepository;
    protected final PatientRepository patientRepository;


    protected UserService(BCryptPasswordEncoder bCryptPasswordEncoder, DoctorRepository doctorRepository, PatientRepository patientRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    public void registerUser(User user, String plainPassword) {
        String encodedPassword = bCryptPasswordEncoder.encode(plainPassword);
        user.setPassword(encodedPassword);
    }

//    public Optional<User> authenticateUser(String email, String password) {
//        Optional<? extends User> userOptional = findByEmail(email);
//        if (userOptional.isPresent()) {
//            User user = userOptional.get();
//            if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
//                return Optional.of(user); // Return user if credentials are valid
//            }
//        }
//        return Optional.empty();
//    }



    public Optional<? extends User> authenticateUser(String email, String password) {

        //Optional<? extends User> userOptional = findByEmail(email);
//        Optional<Doctor> doc = doctorRepository.findByEmail(email);
//        Optional<Patient> pat = patientRepository.findByEmail(email);
        System.out.println("authenticating user with email " + email);

        Optional<Doctor> doc = doctorRepository.findByEmail(email);
        if (doc.isPresent()) {
            System.out.println("Doctor found with email: " + email);
            User user1 = doc.get();
            if (bCryptPasswordEncoder.matches(password, user1.getPassword())) {
                return Optional.of(user1);
            }
        }


        Optional<Patient> pat = patientRepository.findByEmail(email);
        if (pat.isPresent()) {
            System.out.println("Patient found with email: " + email);
            User user2 = pat.get();
            if (bCryptPasswordEncoder.matches(password, user2.getPassword())) {
                return Optional.of(user2);
            }
        }
        System.out.println("No user found with email: " + email);
        return Optional.empty();
    }

        public abstract Optional<? extends User> findByEmail (String email);

        //public abstract void registerSpecificUser(User user);
}
