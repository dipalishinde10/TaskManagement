package com.TaskManagement.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class CloudinaryStorageService {

    private final Cloudinary cloudinary;

    public CloudinaryStorageService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String store(MultipartFile file, String folder) {
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap("folder", folder, "resource_type", "auto"));
            return (String) uploadResult.get("url");  // Correct key
        } catch (IOException e) {
            throw new RuntimeException("File upload failed", e);
        }
    }

    public byte[] read(String storagePath) {
        try {
            URL url = new URL(storagePath);
            URLConnection connect = url.openConnection();
            try (InputStream in = connect.getInputStream();
                 ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {

                byte[] temp = new byte[4096];
                int bytesRead;

                while ((bytesRead = in.read(temp)) != -1) {
                    buffer.write(temp, 0, bytesRead);
                }

                return buffer.toByteArray();
            }
        } catch (Exception e) {
            throw new UnsupportedOperationException("Cloudinary files are accessed via URL");
        }
    }

    public String extractFileName(String storagePath) {
        return storagePath.substring(storagePath.lastIndexOf("/") + 1);
    }
}
