package com.example.demo.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.example.demo.entity.OtpData;
import com.example.demo.entity.User;
import com.example.demo.Repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repo;
    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void register(User user) {

        if (repo.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (user.getRole() == null) {
            user.setRole("USER");
        }

        repo.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }

//    public void resetPassword(String username, String newPassword) {
//
//        User user = repo.findByUsername(username)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        user.setPassword(passwordEncoder.encode(newPassword));
//        repo.save(user);
//    }
private Map<String, OtpData> otpStorage = new HashMap<>();


    // Generate OTP
    public void generateOtp(String username) {

        User user = repo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String otp = String.valueOf(new Random().nextInt(900000) + 100000);

        long expiryTime = System.currentTimeMillis() + (5 * 60 * 1000);

        otpStorage.put(username, new OtpData(otp, expiryTime));

        // 📧 Send OTP to email (assuming username = email)
        emailService.sendOtp(user.getEmail(), otp);
    }

    // Verify OTP and reset password
    public void resetPasswordWithOtp(String username, String otp, String newPassword) {

        OtpData otpData = otpStorage.get(username);

        if (otpData == null) {
            throw new RuntimeException("OTP not generated");
        }

        // ⏳ Check expiry
        if (System.currentTimeMillis() > otpData.getExpiryTime()) {
            otpStorage.remove(username);
            throw new RuntimeException("OTP expired");
        }

        // ❌ Check correctness
        if (!otpData.getOtp().equals(otp)) {
            throw new RuntimeException("Invalid OTP");
        }

        // ✅ Reset password
        User user = repo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(passwordEncoder.encode(newPassword));
        repo.save(user);

        otpStorage.remove(username); // cleanup
    }
}

