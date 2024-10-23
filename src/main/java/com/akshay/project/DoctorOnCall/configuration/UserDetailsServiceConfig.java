package com.akshay.project.DoctorOnCall.configuration;

import com.akshay.project.DoctorOnCall.entity.Doctor;
import com.akshay.project.DoctorOnCall.entity.Patient;
import com.akshay.project.DoctorOnCall.enums.ROLE;
import com.akshay.project.DoctorOnCall.repository.DoctorRepository;
import com.akshay.project.DoctorOnCall.repository.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class UserDetailsServiceConfig {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDetailsServiceConfig(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Bean
    CommandLineRunner commandLineRunner(PatientRepository patientRepository, DoctorRepository doctorRepository
            //, UserRepository userRepository
            , BCryptPasswordEncoder bCryptPasswordEncoder) {
        return args -> {
            // Create a Patient entity
            Patient patient = new Patient();
            patient.setEmail("patient@gmail.com");
            patient.setPassword(bCryptPasswordEncoder.encode("patient"));
            patient.setRole(ROLE.PATIENT);

            // Create a Doctor entity
            Doctor doctor = new Doctor();
            doctor.setEmail("doctor@gmail.com");
            doctor.setPassword(bCryptPasswordEncoder.encode("doctor"));
            doctor.setRole(ROLE.DOCTOR);

            // Create an Admin (assuming Admin is a type of User)
//            User admin = new User();
//            admin.setEmail("admin@gmail.com");
//            admin.setPassword(bCryptPasswordEncoder.encode("admin"));
//            admin.setRole(ROLE.ADMIN);

            // Save each entity to the respective repositories
            patientRepository.save(patient);
            doctorRepository.save(doctor);
        };
    }




    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("patient@gmail.com")
                .password(bCryptPasswordEncoder.encode("patient"))
                .roles("PATIENT")
                .build();

        UserDetails doctor = User.builder()
                .username("doctor@gmail.com")
                .password(bCryptPasswordEncoder.encode("doctor"))
                .roles("DOCTOR")
                .build();

        UserDetails admin = User.builder()
                .username("admin@gmail.com")
                .password(bCryptPasswordEncoder.encode("admin"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, doctor, admin);
    }
}
