package com.rk.musify.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rk.musify.service.DownloadService;

@RestController
@RequestMapping("/download")
public class DownloadController {
	@Autowired
	DownloadService downloadService;

	/*
	 * @GetMapping("/file/{filename}")
	 * 
	 * @ResponseBody public ResponseEntity<?> getFile(@PathVariable("filename")
	 * String filename) { Optional<MusicFile> musicFile =
	 * downloadService.load(filename); if (musicFile.isPresent()) { return
	 * ResponseEntity.ok() .header(HttpHeaders.CONTENT_DISPOSITION,
	 * "attachment; filename=\"" + musicFile.get().getName() + "\"")
	 * .body(musicFile.get().getBlob()); } else { return
	 * ResponseEntity.status(HttpStatus.NOT_FOUND).body("File is not present!!"); }
	 * }
	 */
}
