package com.test.ALabs.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "file_metadata")
public class FileMetadata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String uniqueUrl;  // Unique identifier for the file

    @Column(nullable = false)
    private String filePath;  // Location of the encrypted file

    @Column(nullable = false)
    private LocalDateTime uploadTime;  // Timestamp when file was uploaded

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUniqueUrl() { return uniqueUrl; }
    public void setUniqueUrl(String uniqueUrl) { this.uniqueUrl = uniqueUrl; }

    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }

    public LocalDateTime getUploadTime() { return uploadTime; }
    public void setUploadTime(LocalDateTime uploadTime) { this.uploadTime = uploadTime; }
}