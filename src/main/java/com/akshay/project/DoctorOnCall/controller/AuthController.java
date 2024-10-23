package com.akshay.project.DoctorOnCall.controller;

import com.akshay.project.DoctorOnCall.dtos.LoginDTO;
import com.akshay.project.DoctorOnCall.dtos.RegisterDTO;
import com.akshay.project.DoctorOnCall.entity.Doctor;
import com.akshay.project.DoctorOnCall.entity.Patient;
import com.akshay.project.DoctorOnCall.entity.User;
import com.akshay.project.DoctorOnCall.enums.ROLE;
import com.akshay.project.DoctorOnCall.service.DoctorService;
import com.akshay.project.DoctorOnCall.service.PatientService;
import com.akshay.project.DoctorOnCall.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class AuthController {

    private final DoctorService doctorService;
    private final PatientService patientService;
    private final UserService userService;


    @Autowired
    public AuthController(DoctorService doctorService, PatientService patientService, UserService userService) {
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.userService = userService;
    }

    @GetMapping("/customLogin")
    public String getLoginPage(@RequestParam(value = "success", required = false) String success,
                               @RequestParam(value = "error", required = false) String error,
                               Model model) {
        model.addAttribute("loginDTO", new LoginDTO());
        System.out.println(
                "Login page loaded"
        );
        if (success != null) {
            model.addAttribute("message", "Registration successful! Please log in.");

        }
        if (error != null) {
            model.addAttribute("message", "Login failed! Please try again.");
            //return "redirect:/index";
        }

        return "login";
    }

//    @GetMapping("/customLogin")
//    public String redirectToLogin() {
//        return "redirect:/doctor/home";
//    }


    @PostMapping(value = "/customLogin")
    public String loginUser(@Valid @ModelAttribute LoginDTO loginDTO, Model model) {
        System.out.println(
                "Login processing.."
        );
        Optional<? extends User> user1 = userService.authenticateUser(loginDTO.getLog_email(), loginDTO.getLog_password());

        if (user1.isPresent()) {
            User user = user1.get();
            System.out.println("Login successful for user: " + user.getEmail());
            // Logic for successful login, such as setting session attributes
            //model.addAttribute("registerDTO", registerDTO);
            switch (user.getRole()) {
                case DOCTOR:
                    return "redirect:/doctor/home";
                case PATIENT:
                    return "redirect:/patient/home";
                case ADMIN:
                    return "redirect:/admin/home";
                default:
                    return "redirect:/index";
            }

        } else {
            System.out.println("Login failed for email: " + loginDTO.getLog_email());
            model.addAttribute("error", "Invalid email or password");
            return "redirect:/customLogin?error";
        }
    }


    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new RegisterDTO());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute RegisterDTO registerDTO) {
        try {
            if (registerDTO.getReg_role() == ROLE.DOCTOR) {
                Doctor doctor = new Doctor();
                doctor.setName(registerDTO.getReg_name());
                doctor.setEmail(registerDTO.getReg_email());
                doctor.setPassword(registerDTO.getReg_password());
                doctor.setRole(ROLE.DOCTOR);
                doctorService.registerDoctor(doctor);
            } else if (registerDTO.getReg_role() == ROLE.PATIENT) {
                Patient patient = new Patient();
                patient.setName(registerDTO.getReg_name());
                patient.setEmail(registerDTO.getReg_email());
                patient.setPassword(registerDTO.getReg_password());
                patient.setRole(ROLE.PATIENT);
//                patientService.registerSpecificUser(patient);
                patientService.registerPatient(patient);
            }
            return "redirect:/customLogin?success";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/register?error";
        }
    }


    @GetMapping("/index")
    public String getHomePage() {
        return "index";
    }

    @GetMapping("/doctor/home")
    public String getDoctorHome() {
        return "doctorHome";
    }

    @GetMapping("/patient/home")
    public String getPatientHome() {
        return "patientHome";
    }

//    @GetMapping("/login?error")
//    public String getLoginError() {
//        return "redirect:/login";
//    }

}
////    public UserModel findByLogin(String login) {
////        return users.stream().filter(user -> user.getUsername().equals(login))
////                .findFirst()
////                .orElse(null);
////    }
//}
