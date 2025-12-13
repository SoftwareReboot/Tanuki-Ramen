package com.example.rtbackend.services;

import java.nio.file.Path;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    String storeFile(MultipartFile file);
    Path getFilePath(String fileName);
}
