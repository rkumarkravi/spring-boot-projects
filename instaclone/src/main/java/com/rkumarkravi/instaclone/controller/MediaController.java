package com.rkumarkravi.instaclone.controller;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rkumarkravi.instaclone.dto.UploadRequest;
import com.rkumarkravi.instaclone.dto.XmlReq;
import com.rkumarkravi.instaclone.entity.IMedia;
import com.rkumarkravi.instaclone.entity.IUser;
import com.rkumarkravi.instaclone.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.rkumarkravi.instaclone.util.Utils.isJson;

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
        return uploadService.getUploadById(uploadId).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<IMedia> createUpload(@RequestBody UploadRequest uploadRequest) {
        // Assuming UploadRequest is a DTO with necessary fields
        IUser user = null; // get the user from the authentication context or request
        IMedia createdUpload = uploadService.createUpload(user, uploadRequest.getImageUrl());
        return ResponseEntity.ok(createdUpload);
    }

    @PostMapping(value = "/test"
    )
    public String testController(@RequestBody XmlReq req) {
        System.out.println(req.getMisc());
        if (req.getMisc() != null && isJson(req.getMisc())) {
            List<Map<String,String>> map=new Gson().fromJson(req.getMisc(), new TypeToken<List<Map<String, String>>>() {
            }.getType());
            System.out.println(map.get(0).get("hey"));
        }else{
            return "some problem occurred";
        }
        return req.toString();

    }
    // Other upload-related endpoints
}

