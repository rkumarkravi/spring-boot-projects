package com.rk.animestream.controller;

import com.rk.animestream.service.DownloadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/download")
@Slf4j
public class VideoDownloader {
    @Autowired
    DownloadService downloadService;
    @GetMapping(value = "/{aid}", produces = "video/mp4")
    public Mono<byte[]> getVideos(@PathVariable("aid") Long aid, @RequestHeader("Range") String range) {
        log.debug("range in bytes() : " + range);
        return downloadService.getVideo(aid);
    }

    @GetMapping(value = "/fromRes/{vid}", produces = "*/*")
    public Mono<Resource> getVideosByResource(@PathVariable("vid") Long aid) {
        //log.debug("range in bytes() : " + range);
        return downloadService.getVideoFromResource(aid);
    }

    @GetMapping(value = "/thumbnail/{aid}", produces ={ MediaType.IMAGE_PNG_VALUE,MediaType.IMAGE_JPEG_VALUE})
    public Mono<Resource> getThumbnailByResource(@PathVariable("aid") Long aid) {
        return downloadService.getThumbnailResource(aid);
    }
}
