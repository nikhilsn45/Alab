package com.test.ALabs.controller;

import com.test.ALabs.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;

@RestController
@RequestMapping("/api/files")
public class FileController {
    @Autowired
    private FileService fileService;

    @PostMapping("/")
    public String upload(){
        return "upload.html";
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,
                                             @RequestParam("passcode") String passcode) throws Exception {
        File tempFile = File.createTempFile("upload_", file.getOriginalFilename());
        file.transferTo(tempFile);

        String uniqueUrl = fileService.uploadFile(tempFile, passcode);
        return ResponseEntity.ok("File uploaded. Unique URL: " + uniqueUrl);
    }

    @GetMapping("/download/{uniqueUrl}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String uniqueUrl,
                                               @RequestParam("passcode") String passcode) throws Exception {
        File decryptedFile = fileService.downloadFile(uniqueUrl, passcode);
        byte[] fileContent = Files.readAllBytes(decryptedFile.toPath());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + decryptedFile.getName())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(fileContent);
    }
}