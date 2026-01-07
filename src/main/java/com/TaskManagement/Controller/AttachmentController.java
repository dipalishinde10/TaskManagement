package com.TaskManagement.Controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.TaskManagement.Service.AttachmentService;

@RestController
@RequestMapping("/api/attachments")
public class AttachmentController {

    private final AttachmentService attachmentService;

    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    // Upload file
    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file,
                                         @RequestParam(value = "folder", defaultValue = "default") String folder) {
        String fileUrl = attachmentService.uploadFile(file, folder);
        return ResponseEntity.ok(fileUrl);
    }

    // Optional: Download file bytes
    @GetMapping("/download")
    public ResponseEntity<byte[]> download(@RequestParam("url") String fileUrl) {
        byte[] data = attachmentService.getFile(fileUrl);
        String fileName = attachmentService.getFileName(fileUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData(fileName, fileName);

        return new ResponseEntity<>(data, headers, HttpStatus.OK);
    }
}
