package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@Service
public class StegoService {

    @Autowired
    private CryptoService cryptoService;

    // ================= EMBED =================
    public byte[] embed(MultipartFile file, String message) throws Exception {

        BufferedImage image = ImageIO.read(file.getInputStream());

        if (image == null) {
            throw new Exception("Invalid image (use PNG)");
        }

        // Encrypt + End marker
        message = cryptoService.encrypt(message) + "\0";

        int width = image.getWidth();
        int height = image.getHeight();

        int maxChars = (width * height) / 8;
        if (message.length() > maxChars) {
            throw new Exception("Message too large for this image");
        }

        int msgIndex = 0;
        int charIndex = 0;

        outer:
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                if (msgIndex >= message.length()) break outer;

                int pixel = image.getRGB(x, y);

                int charVal = message.charAt(msgIndex);
                int bit = (charVal >> (7 - charIndex)) & 1;

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
            throw new Exception("Invalid image");
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

                    if (charCode == 0) {
                        return cryptoService.decrypt(message.toString());
                    }

                    message.append((char) charCode);
                    binary.setLength(0);
                }
            }
        }

        return message.toString();
    }
}