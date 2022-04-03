package com.rk.musify.service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rk.musify.model.dao.MusicFile;
import com.rk.musify.repository.MusicFileDao;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DownloadService {
	private final Path root = Paths.get("C:\\uploads");

	@Autowired
	private MusicFileDao musicFileDao;

	public Optional<MusicFile> load(String filename) {
		Optional<MusicFile> fileDB = null;
		fileDB = musicFileDao.findById(filename);
		log.debug("file is present: {}",fileDB.isPresent());
		return fileDB;
	}
	/*
	 * public Resource load(String filename) { try { Path file =
	 * root.resolve(filename); log.info("file path:{}"+file.getFileName()); Resource
	 * resource = new UrlResource(file.toUri()); if (resource.exists() ||
	 * resource.isReadable()) { return resource; } else { throw new
	 * RuntimeException("Could not read the file!"); } } catch
	 * (MalformedURLException e) { throw new RuntimeException("Error: " +
	 * e.getMessage()); } }
	 */}
