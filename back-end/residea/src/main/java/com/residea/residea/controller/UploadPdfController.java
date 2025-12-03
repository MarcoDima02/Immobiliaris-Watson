package com.residea.residea.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/upload")
public class UploadPdfController {

    @Value("${contratti.upload-dir}")
    private String uploadDir;

    @PostMapping("/pdf")
    public ResponseEntity<?> uploadPdf(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File vuoto");
        }

        // controlla estensione
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        if (!filename.toLowerCase().endsWith(".pdf")) {
            return ResponseEntity.badRequest().body("Solo file PDF consentiti");
        }

        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // genera nome unico per evitare conflitti
            String uniqueName = System.currentTimeMillis() + "_" + filename;
            Path targetPath = uploadPath.resolve(uniqueName);
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            // restituisci percorso relativo per il DB
            String dbPath = "/uploads/contratti/" + uniqueName;
            return ResponseEntity.ok(dbPath);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Errore salvataggio file: " + e.getMessage());
        }
    }



    
}


