package com.test.ALabs.service;

import com.test.ALabs.model.FileMetadata;
import com.test.ALabs.repository.FileMetadataRepository;
import com.test.ALabs.utils.FileEncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class FileService {
    private static final String STORAGE_DIR = "/tmp/files/";

    @Autowired
    private FileMetadataRepository repository;

    public String uploadFile(File file, String passcode) throws Exception {
        // Create a unique URL for the file
        String uniqueUrl = UUID.randomUUID().toString();
        File encryptedFile = new File(STORAGE_DIR + uniqueUrl + ".enc");

        // Encrypt the file
        FileEncryptionUtil.encryptFile(file, encryptedFile, passcode);

        // Save metadata to DB
        FileMetadata metadata = new FileMetadata();
        metadata.setUniqueUrl(uniqueUrl);
        metadata.setFilePath(encryptedFile.getAbsolutePath());
        metadata.setUploadTime(LocalDateTime.now());
        repository.save(metadata);

        return uniqueUrl;
    }

    public File downloadFile(String uniqueUrl, String passcode) throws Exception {
        Optional<FileMetadata> optionalMetadata = repository.findByUniqueUrl(uniqueUrl);
        if (optionalMetadata.isPresent()) {
            FileMetadata metadata = optionalMetadata.get();
            File encryptedFile = new File(metadata.getFilePath());
            File decryptedFile = new File(STORAGE_DIR + "decrypted_" + uniqueUrl);

            // Decrypt the file
            FileEncryptionUtil.decryptFile(encryptedFile, decryptedFile, passcode);
            return decryptedFile;
        } else {
            throw new RuntimeException("File not found or expired!");
        }
    }

    public void deleteExpiredFiles() {
        LocalDateTime cutoffTime = LocalDateTime.now().minusHours(48);
        repository.deleteByUploadTimeBefore(cutoffTime);
    }
}
