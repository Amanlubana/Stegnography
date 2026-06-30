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

        String json = """
        {
          "sender": {
            "name": "Steganography App",
            "email": "ambusy24o7@gmail.com"
          },
          "to": [{
            "email": "%s"
          }],
          "subject": "OTP for Password Reset",
          "htmlContent": "<h2>Your OTP is <b>%s</b></h2><p>Valid for 5 minutes.</p>"
        }
        """.formatted(toEmail, otp);

        RequestBody body = RequestBody.create(
                json,
                MediaType.parse("application/json")
        );

        Request request = new Request.Builder()
                .url("https://api.brevo.com/v3/smtp/email")
                .post(body)
                .addHeader("accept", "application/json")
                .addHeader("api-key", apiKey)
                .addHeader("content-type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {

            if (!response.isSuccessful()) {
                throw new RuntimeException("Email failed: " + response.body().string());
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
