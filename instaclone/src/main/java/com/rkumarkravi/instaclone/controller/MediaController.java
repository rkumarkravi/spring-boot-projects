package com.rkumarkravi.instaclone.controller;

import com.rkumarkravi.instaclone.dto.UploadRequest;
import com.rkumarkravi.instaclone.entity.IMedia;
import com.rkumarkravi.instaclone.entity.IUser;
import com.rkumarkravi.instaclone.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/media")
public class MediaController {

    private final MediaService uploadService;

    @Autowired
    public MediaController(MediaService uploadService) {
        this.uploadService = uploadService;
    }

    @GetMapping
    public ResponseEntity<List<IMedia>> getAllUploads() {
        List<IMedia> uploads = uploadService.getAllUploads();
        return ResponseEntity.ok(uploads);
    }

    @GetMapping("/{uploadId}")
    public ResponseEntity<IMedia> getUploadById(@PathVariable Long uploadId) {
        return uploadService.getUploadById(uploadId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<IMedia> createUpload(@RequestBody UploadRequest uploadRequest) {
        // Assuming UploadRequest is a DTO with necessary fields
        IUser user = null; // get the user from the authentication context or request
        IMedia createdUpload = uploadService.createUpload(user, uploadRequest.getImageUrl());
        return ResponseEntity.ok(createdUpload);
    }

    // Other upload-related endpoints
}

