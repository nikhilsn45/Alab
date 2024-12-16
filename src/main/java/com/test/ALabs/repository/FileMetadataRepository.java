package com.test.ALabs.repository;

import com.test.ALabs.model.FileMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface FileMetadataRepository extends JpaRepository<FileMetadata, Long> {
    Optional<FileMetadata> findByUniqueUrl(String uniqueUrl);
    void deleteByUploadTimeBefore(LocalDateTime time);
}