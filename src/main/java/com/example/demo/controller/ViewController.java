package com.example.demo.controller;

import com.example.demo.Service.StegoService;
import com.example.demo.util.AESUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ViewController {

    private final StegoService stegoService;

    public ViewController(StegoService stegoService) {
        this.stegoService = stegoService;
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/embed")
    public String embedPage() {
        return "embed";
    }

    @PostMapping("/embed")
    public ResponseEntity<byte[]> embed(
            @RequestParam("file") MultipartFile file,
            @RequestParam("message") String message,
            @RequestParam("password") String password
    ) throws Exception {

        String encrypted = AESUtil.encrypt(message, password);

        byte[] result = stegoService.embed(file, encrypted);


        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=stego.png")
                .header("Content-Type", "image/png")
                .body(result);
    }

    @GetMapping("/extract")
    public String extractPage() {
        return "extract";
    }


    @PostMapping("/extract")
    public String extract(
            @RequestParam("file") MultipartFile file,
            @RequestParam("password") String password,
            Model model) {

        try {
            String encrypted = stegoService.extract(file);
            System.out.println("ENCRYPTED: " + encrypted);

            String decrypted = AESUtil.decrypt(encrypted, password);
            System.out.println("DECRYPTED: " + decrypted);

            model.addAttribute("message", decrypted);

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e.getMessage()); // 👈 show real error
        }

        return "extract";
    }
}