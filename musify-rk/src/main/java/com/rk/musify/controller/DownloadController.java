package com.rk.musify.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rk.musify.model.dao.MusicBlob;
import com.rk.musify.service.DownloadService;

@RestController
@RequestMapping("/download")
@CrossOrigin(origins = "http://localhost:4200")
public class DownloadController {
	@Autowired
	DownloadService downloadService;

	@GetMapping("/file/{filename}")
	@ResponseBody
	public ResponseEntity<?> getFile(@PathVariable("filename") String blobId) {
		Optional<MusicBlob> musicBlob = downloadService.load(blobId);
		if (musicBlob.isPresent()) {
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION,
							"attachment; filename=\"" + musicBlob.get().getId() + "\"")
					.body(musicBlob.get().getBlob());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Song not present!!");
		}
	}

}
