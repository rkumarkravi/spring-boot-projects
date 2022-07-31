package com.rk.animestream.service;

import com.google.gson.Gson;
import com.rk.animestream.daos.AnimeRepository;
import com.rk.animestream.daos.ThumbnailRepository;
import com.rk.animestream.daos.VideoBlobRepository;
import com.rk.animestream.daos.VideoRepository;
import com.rk.animestream.dtos.VideoDto;
import com.rk.animestream.mapper.VideoMapper;
import com.rk.animestream.models.Anime;
import com.rk.animestream.models.Thumbnail;
import com.rk.animestream.models.Video;
import com.rk.animestream.models.VideoBlob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UploadService {
    @Autowired
    AnimeRepository animeRepository;

    @Autowired
    ThumbnailRepository thumbnailRepository;

    @Autowired
    VideoRepository videoRepository;

    @Autowired
    VideoMapper videoMapper;

    @Autowired
    VideoBlobRepository videoBlobRepository;


    Gson gson=new Gson();
    public VideoDto uploadFileToDb(Long aid, String videoDetails, List<byte[]> videoFiles, byte[] thumbnail){


        Thumbnail thumbnail1=new Thumbnail();
        thumbnail1.setTBlob(thumbnail);
//        thumbnail1=thumbnailRepository.save(thumbnail1);

        VideoBlob videoBlob = new VideoBlob();
        videoBlob.setVBlob(videoFiles.get(0));
//        videoBlob=videoBlobRepository.save(videoBlob);

        Map<String, String> map=gson.fromJson(java.lang.String.valueOf(videoDetails), HashMap.class);
        Video video=new Video();
        video.setTitle(map.get("title"));
        video.setTotalTime(map.get("totalTime"));

        video.setThumbnail(thumbnail1);

        video.setVideoBlob(videoBlob);

        video=videoRepository.save(video);

        Anime anime =animeRepository.findById(aid).get();
        anime.getVideos().add(video);
        animeRepository.save(anime);

        return videoMapper.videoToVideoDto(video);
    }
}
