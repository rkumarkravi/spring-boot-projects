package com.rk.musify.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rk.musify.model.dao.MusicFile;
import com.rk.musify.repository.MusicFileDao;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DownloadService {

	@Autowired
	private MusicFileDao musicFileDao;

	public Optional<MusicFile> load(String filename) {
		Optional<MusicFile> fileDB = null;
		fileDB = musicFileDao.findById(filename);
		log.debug("file is present: {}",fileDB.isPresent());
		return fileDB;
	}
}
