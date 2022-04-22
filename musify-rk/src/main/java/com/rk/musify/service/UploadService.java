package com.rk.musify.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rk.musify.model.dao.Album;
import com.rk.musify.model.dao.MusicBlob;
import com.rk.musify.model.dao.MusicFile;
import com.rk.musify.repository.AlbumDao;
import com.rk.musify.repository.MusicBlobDao;
import com.rk.musify.repository.MusicFileDao;

@Service
public class UploadService {
	@Autowired
	MusicFileDao musicFileDao;

	@Autowired
	AlbumDao albumDao;

	@Autowired
	MusicBlobDao musicBlobDao;

	public void save(MultipartFile file, String aid) {
		Album album = albumDao.getById(aid);
		try {
			String filename = file.getOriginalFilename();

			MusicBlob blob=musicBlobDao.save(new MusicBlob(file.getBytes()));
			
			MusicFile music = new MusicFile(filename, file.getContentType(),blob.getId(),false);
			album.getMusicFiles().add(music);
			
			albumDao.save(album);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
		}
	}
}
