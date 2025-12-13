package com.example.rtbackend.services.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.rtbackend.services.FileStorageService;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final Path uploadDir;

    public FileStorageServiceImpl(@Value("${file.upload-dir}") String uploadDir) {
        this.uploadDir = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.uploadDir);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create upload directory!", ex);
        }
    }
    
    @Override
    public String storeFile(MultipartFile file) {
        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
        if (originalFilename.contains("..")) {
            throw new RuntimeException("Filepath contains invalid path sequence " + originalFilename);
        }

        String fileExtension = "";
        int i = originalFilename.lastIndexOf('.');
        if (i > 0) fileExtension = originalFilename.substring(i + 1);

        String fileName = System.currentTimeMillis() + "_" + java.util.UUID.randomUUID() + "." + fileExtension;

        try {
            Path targetLocation = this.uploadDir.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", e); 
        }
    }

    @Override
    public Path getFilePath(String fileName) {
        return this.uploadDir.resolve(fileName).normalize();
    }

}
