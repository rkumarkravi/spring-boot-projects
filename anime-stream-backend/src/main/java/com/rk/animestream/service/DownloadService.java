package com.rk.animestream.service;

import com.rk.animestream.daos.ThumbnailRepository;
import com.rk.animestream.daos.VideoBlobFileRepository;
import com.rk.animestream.models.Thumbnail;
import com.rk.animestream.models.VideoBlobFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class DownloadService {
    @Autowired
    private VideoBlobFileRepository videoBlobFileRepository;
    @Autowired
    private ThumbnailRepository thumbnailRepository;
    @Autowired
    private ResourceLoader resourceLoader;
    public Mono<byte[]> getVideo(long blobId){
        VideoBlobFile videoBlobObj=videoBlobFileRepository.getReferenceById(blobId);
        byte[] videoBlob=videoBlobObj.getVBlob();
        ByteArrayResource byteArrayResource=new ByteArrayResource(videoBlob);
        return Mono.fromSupplier(()->byteArrayResource.getByteArray());
    }
    private static String OS = System.getProperty("os.name").toLowerCase();

    @Value("${anime.videofiles.path}")
    String videoPath;

    @Value("${anime.thumbnailfiles.path}")
    String thumbnailPath;
    public Mono<Resource> getVideoFromResource(long blobId){
        VideoBlobFile videoBlobObj=videoBlobFileRepository.getReferenceById(blobId);
        String path=videoPath;
        if(OS.contains("win")){
            path=videoPath;
        }
        String videoFileName=videoBlobObj.getVFile();
        String videoFullPath=path+videoFileName;
        log.debug("filepath {}",videoFullPath);
        Resource res= new FileSystemResource(videoFullPath);
        return Mono.fromSupplier(()->res);
    }

    public Mono<Resource> getThumbnailResource(long blobId){
        Thumbnail thumbnail=thumbnailRepository.getReferenceById(blobId);
        String path=thumbnailPath;
        if(OS.contains("win")){
            path=thumbnailPath;
        }
        String thumbnailFileName=thumbnail.getFile();
        String thumbnailFullPath=path+thumbnailFileName;
        log.debug("filepath {}",thumbnailFullPath);
        Resource res= new FileSystemResource(thumbnailFullPath);
        return Mono.fromSupplier(()->res);
    }
}
