package com.example.demo.controller;

import com.example.demo.Repo.UserRepository;
import com.example.demo.Service.EmailService;
import com.example.demo.Service.UserService;
import com.example.demo.entity.OtpData;
import com.example.demo.entity.User;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Controller
public class AuthController {

    private final UserService userService;

    private final EmailService emailService;


    private final UserRepository repo;



    public AuthController(UserService userService, EmailService emailService, UserRepository repo) {
        this.userService = userService;
        this.emailService = emailService;
        this.repo = repo;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User()); // REQUIRED
        return "register";
    }
    @PostMapping("/register")
    public String registerUser(
            @Valid @ModelAttribute("user") User user,
            BindingResult result,
            Model model) {

        // 1. Validation errors
        if (result.hasErrors()) {
            return "register";
        }

        try {
            // 2. Register user (includes duplicate check + password encoding)
            userService.register(user);

            return "redirect:/login";

        } catch (Exception e) {
            // 3. Handle duplicate username or other errors
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {
        return "forgot-password";
    }



    // Step 2: Verify OTP + Reset password
    @PostMapping("/reset-password")
    public String resetPassword(
            @RequestParam String username,
            @RequestParam String otp,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword,  // ✅ ADD THIS
            Model model) {

        // 🔥 Check password match
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match");
            model.addAttribute("username", username);
            return "verify-otp";
        }

        try {
            userService.resetPasswordWithOtp(username, otp, newPassword);
            return "redirect:/login?resetSuccess";

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("username", username);
            return "verify-otp";
        }
    }
        @PostMapping("/generate-otp")
        public String generateOtp(@RequestParam String username, Model model) {
            userService.generateOtp(username);
            model.addAttribute("username", username); // 🔥 REQUIRED
            return "verify-otp";
        }
}



