package com.rk.animestream.controller;

import com.rk.animestream.dtos.VideoDto;
import com.rk.animestream.service.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class VideoUploader {
    @Autowired
    UploadService uploadService;

    @PostMapping(path = "/upload/{aid}",consumes ={MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<VideoDto> uploadVideos(@PathVariable("aid") Long aid, @RequestPart("videoDetails") String videoDetails, @RequestPart("videoFiles") List<MultipartFile> videoFileList, @RequestPart("thumbnail") MultipartFile thumbnail) throws Exception {
        log.debug("aid:{},{},{},{}",aid,videoDetails,videoFileList,thumbnail);
        List<byte[]> videoFiles=new ArrayList<>();
        for(MultipartFile videoFile:videoFileList) {
            if (!videoFile.getOriginalFilename().split("\\.")[1].equals("mp4")) {
                throw new Exception("File not supported!");
            } else {
                videoFiles.add(videoFile.getBytes());
            }
        }
        return new ResponseEntity<VideoDto>(uploadService.uploadFileToDb(aid,videoDetails,videoFiles,thumbnail.getBytes()),HttpStatus.OK);
    }
}
