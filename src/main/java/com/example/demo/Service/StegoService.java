package com.example.demo.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@Service
public class StegoService {





    // ================= EMBED =================
    public byte[] embed(MultipartFile file, String message) throws Exception {

        if (!file.getContentType().equals("image/png")) {
            throw new RuntimeException("Only PNG images are allowed");
        }

        BufferedImage image = ImageIO.read(file.getInputStream());

        if (image == null) {
            throw new Exception("Invalid image");
        }


        // Add end marker
        String finalMessage = message + "\0";

        int width = image.getWidth();
        int height = image.getHeight();

        int maxChars = (width * height) / 8;
        if (finalMessage.length() > maxChars) {
            throw new Exception("Message too large for this image.");
        }

        int msgIndex = 0;
        int charIndex = 0;

        outer:
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                if (msgIndex >= finalMessage.length()) break outer;

                int pixel = image.getRGB(x, y);

                int charVal = finalMessage.charAt(msgIndex);
                int bit = (charVal >> (7 - charIndex)) & 1;

                // Modify only LSB
                pixel = (pixel & 0xFFFFFFFE) | bit;

                image.setRGB(x, y, pixel);

                charIndex++;

                if (charIndex == 8) {
                    charIndex = 0;
                    msgIndex++;
                }
            }
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);




        return baos.toByteArray();
    }

    // ================= EXTRACT =================
    public String extract(MultipartFile file) throws Exception {

        BufferedImage image = ImageIO.read(file.getInputStream());

        if (image == null) {
            throw new Exception("Invalid image.");
        }

        StringBuilder binary = new StringBuilder();
        StringBuilder message = new StringBuilder();

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {

                int pixel = image.getRGB(x, y);
                int lsb = pixel & 1;

                binary.append(lsb);

                if (binary.length() == 8) {
                    int charCode = Integer.parseInt(binary.toString(), 2);

                    // End marker reached
                    if (charCode == 0) {
                        return message.toString();
                    }

                    message.append((char) charCode);
                    binary.setLength(0);
                }
            }
        }

        // If no end marker found → likely no hidden data
        throw new Exception("No hidden message found in image.");
    }
}