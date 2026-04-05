package com.example.demo.controller;

import com.example.demo.Service.StegoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ViewController {

    private final StegoService stegoService;

    public ViewController(StegoService stegoService) {
        this.stegoService = stegoService;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/embed")
    public String embedPage() {
        return "embed";
    }

    @GetMapping("/extract")
    public String extractPage() {
        return "extract";
    }

    @PostMapping("/extract")
    public String extract(@RequestParam("file") MultipartFile file, Model model) throws Exception {
        String message = stegoService.extract(file);
        model.addAttribute("message", message);
        return "extract";
    }
}