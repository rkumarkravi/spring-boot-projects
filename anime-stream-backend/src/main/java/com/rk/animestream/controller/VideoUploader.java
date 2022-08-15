package com.rk.animestream.controller;

import com.rk.animestream.dtos.VideoDto;
import com.rk.animestream.service.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class VideoUploader {

    @Autowired
    UploadService uploadService;

    @Value("${anime.videofiles.path}")
    String videoPath;

    @Value("${anime.thumbnailfiles.path}")
    String thumbnailPath;
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

    @PostMapping(path = "/uploadFileUrl/{aid}/{seasonNo}",consumes ={MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<VideoDto> uploadVideoFileUrl(@PathVariable("aid") Long aid,@PathVariable("seasonNo") Long seasonNo, @RequestPart("videoDetails") String videoDetails, @RequestPart("videoFiles") List<MultipartFile> videoFileList, @RequestPart("thumbnail") MultipartFile thumbnail) throws Exception {
        log.debug("aid:{},{},{},{}",aid,videoDetails,videoFileList,thumbnail);
        String videoFileName=null,thumbnailFileName=null;
        for(MultipartFile videoFile:videoFileList) {
            log.debug("filename {}",videoFile.getOriginalFilename());
            if (!videoFile.getOriginalFilename().split("\\.")[1].equals("mp4")) {
                throw new Exception("File not supported!");
            } else {
                UUID uuidvid=UUID.nameUUIDFromBytes(videoFile.getBytes());
                log.debug("filePathVideo uuid {}",uuidvid);
                Path filePathVideo = Paths.get(videoPath);

                if(!filePathVideo.toFile().exists()){
                    filePathVideo.toFile().mkdirs();
                }
                String originalVideoFileName=videoFile.getOriginalFilename();
                String uploadVideoFileName= uuidvid +originalVideoFileName.substring(originalVideoFileName.indexOf("."));
                filePathVideo=filePathVideo.resolve(uploadVideoFileName);
                log.debug("filePathVideo {}",filePathVideo.toAbsolutePath());
                Files.write(filePathVideo, videoFile.getBytes());
                videoFileName=uploadVideoFileName;
            }
        }

        UUID uuidThumb=UUID.nameUUIDFromBytes(thumbnail.getBytes());
        log.debug("filePathThumbNail uuid {}",uuidThumb);
        Path filePathThumbNail = Paths.get(thumbnailPath);
        if(!filePathThumbNail.toFile().exists()){
            filePathThumbNail.toFile().mkdirs();
        }
        String originalThumbFileName=thumbnail.getOriginalFilename();
        log.debug("filePathThumbNail {}",filePathThumbNail);
        String uploadThumbFileName= uuidThumb+originalThumbFileName.substring(originalThumbFileName.indexOf("."));
        filePathThumbNail=filePathThumbNail.resolve(uploadThumbFileName);
        Files.write(filePathThumbNail, thumbnail.getBytes());
        thumbnailFileName=uploadThumbFileName;
        if(videoFileName==null || thumbnailFileName==null){
            throw new Exception("File is null!");
        }
        return new ResponseEntity<VideoDto>(uploadService.uploadFileToFileServer(aid,seasonNo,videoDetails,videoFileName,thumbnailFileName),HttpStatus.OK);
    }
}
