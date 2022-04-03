package com.rk.musify.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rk.musify.model.dao.MusicFile;
import com.rk.musify.repository.MusicFileDao;

@Service
public class UploadService {
	@Autowired
	MusicFileDao musicFileDao;

	public void save(MultipartFile file) {
		try {
			String filename=file.getOriginalFilename();
			MusicFile f = new MusicFile(filename,file.getContentType(), file.getBytes());
			musicFileDao.save(f);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
		}
	}
}
