package com.example.demo.Service;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Value("${BREVO_API_KEY}")
    private String apiKey;

    private final OkHttpClient client = new OkHttpClient();

    public void sendOtp(String toEmail, String otp) {

    System.out.println("Inside EmailService");

    try {

        MimeMessage message = mailSender.createMimeMessage();

        System.out.println("MimeMessage created");

        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("your_verified_sender@yourdomain.com");
        helper.setTo(toEmail);
        helper.setSubject("OTP");
        helper.setText("Your OTP is " + otp);

        System.out.println("Calling mailSender.send()");

        mailSender.send(message);

        System.out.println("Mail sent successfully");

    } catch (Exception e) {
        e.printStackTrace();
        throw new RuntimeException("Failed to send email");
    }
}
    }
}
