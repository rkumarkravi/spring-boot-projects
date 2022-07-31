package com.rk.animestream.service;

import com.rk.animestream.daos.VideoBlobRepository;
import com.rk.animestream.models.VideoBlob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

@Service
public class DownloadService {
    @Autowired
    private VideoBlobRepository videoBlobRepository;

    public Mono<byte[]> getVideo(long blobId){
        VideoBlob videoBlobObj=videoBlobRepository.getReferenceById(blobId);
        byte[] videoBlob=videoBlobObj.getVBlob();
        return Mono.just(videoBlob)   ;
    }
}
