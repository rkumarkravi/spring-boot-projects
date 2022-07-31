package com.rk.animestream.controller;

import com.rk.animestream.dtos.VideoDto;
import com.rk.animestream.service.DownloadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class VideoDownloader {
    @Autowired
    DownloadService downloadService;
    @GetMapping(value = "download/{aid}", produces = "video/mp4")
    public Mono<byte[]> getVideos(@PathVariable("aid") Long aid, @RequestHeader("Range") String range) {
        System.out.println("range in bytes() : " + range);
        return downloadService.getVideo(aid);
    }
}
