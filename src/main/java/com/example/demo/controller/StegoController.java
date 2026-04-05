package com.example.demo.controller;

import com.example.demo.Service.StegoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/stego")
@CrossOrigin
public class StegoController {

    @Autowired
    private StegoService stegoService;

    @PostMapping("/embed")
    public ResponseEntity<byte[]> embed(@RequestParam("file") MultipartFile file,
                                        @RequestParam("message") String message) throws Exception {

        byte[] imageBytes = stegoService.embed(file, message);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=stego.png")
                .body(imageBytes);
    }

    @PostMapping("/extract")
    public String extract(@RequestParam("file") MultipartFile file) throws Exception {
        return stegoService.extract(file);
    }
}