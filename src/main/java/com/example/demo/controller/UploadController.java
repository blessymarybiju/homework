package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class UploadController {

    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";

    @GetMapping("/")
    public String index() {
        return "upload";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("text") String text, Model model) {
        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a file to upload");
            return "upload";
        }

        try {
            // Save file
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOAD_DIR + file.getOriginalFilename());
            Files.write(path, bytes);

            model.addAttribute("message", "File uploaded successfully: " + file.getOriginalFilename());
            model.addAttribute("text", "Text: " + text);
            model.addAttribute("fileUrl", "/uploads/" + file.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "upload";
    }
}
