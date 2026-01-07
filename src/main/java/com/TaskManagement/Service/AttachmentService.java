package com.TaskManagement.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AttachmentService {

    private final CloudinaryStorageService storageService;

    public AttachmentService(CloudinaryStorageService storageService) {
        this.storageService = storageService;
    }

    // Upload a file to a specific folder
    public String uploadFile(MultipartFile file, String folder) {
        return storageService.store(file, folder);
    }

    // Read file bytes (optional, normally you return URL)
    public byte[] getFile(String fileUrl) {
        return storageService.read(fileUrl);
    }

    // Extract filename from URL
    public String getFileName(String fileUrl) {
        return storageService.extractFileName(fileUrl);
    }
}
