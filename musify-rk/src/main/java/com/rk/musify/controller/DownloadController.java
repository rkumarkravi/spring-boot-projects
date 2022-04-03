package com.rk.musify.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rk.musify.model.dao.MusicFile;
import com.rk.musify.service.DownloadService;

@RestController
@RequestMapping("/download")
public class DownloadController {
	@Autowired
	DownloadService downloadService;

	@GetMapping("/file/{filename}")
	@ResponseBody
	public ResponseEntity<?> getFile(@PathVariable("filename") String filename) {
		Optional<MusicFile> musicFile = downloadService.load(filename);
		if (musicFile.isPresent()) {
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION,
							"attachment; filename=\"" + musicFile.get().getName() + "\"")
					.body(musicFile.get().getBlob());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File is not present!!");
		}
	}
	/*
	 * @GetMapping("/file/{filename}")
	 * 
	 * @ResponseBody public ResponseEntity<Resource>
	 * getFile(@PathVariable("filename") String filename) { Resource file =
	 * downloadService.load(filename); return ResponseEntity.ok()
	 * .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
	 * file.getFilename() + "\"") .body(file); }
	 */}
