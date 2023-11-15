package com.rkumarkravi.instaclone.service;

import com.rkumarkravi.instaclone.entity.IMedia;
import com.rkumarkravi.instaclone.entity.IUser;
import com.rkumarkravi.instaclone.dao.sql.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MediaService {

    private final MediaRepository mediaRepository;

    @Autowired
    public MediaService(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
    }

    public List<IMedia> getAllUploads() {
        return mediaRepository.findAll();
    }

    public Optional<IMedia> getUploadById(Long uploadId) {
        return mediaRepository.findById(uploadId);
    }

    public List<IMedia> getUploadsByUser(IUser user) {
        return mediaRepository.findByUser(user);
    }

    public IMedia createUpload(IUser user, String imageUrl) {
        IMedia upload = new IMedia();
        upload.setUser(user);
        upload.setImageUrl(imageUrl);
        upload.setUploadDateTime(LocalDateTime.now());

        return mediaRepository.save(upload);
    }

    // Other upload-related methods
}

