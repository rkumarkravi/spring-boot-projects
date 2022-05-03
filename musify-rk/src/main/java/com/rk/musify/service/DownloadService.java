package com.rk.musify.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rk.musify.model.dao.MusicBlob;
import com.rk.musify.model.dao.MusicFile;
import com.rk.musify.repository.MusicBlobDao;
import com.rk.musify.repository.MusicFileDao;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DownloadService {

	@Autowired
	private MusicBlobDao musicBlobDao;

	public Optional<MusicBlob> load(String blobId) {
		Optional<MusicBlob> fileDB = null;
		fileDB = musicBlobDao.findById(blobId);
		log.debug("file is present: {}",fileDB.isPresent());
		return fileDB;
	}
}
