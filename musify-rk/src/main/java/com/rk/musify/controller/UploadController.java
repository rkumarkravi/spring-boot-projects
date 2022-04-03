package com.rk.musify.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rk.musify.service.UploadService;

@RestController
@RequestMapping("/upload")
public class UploadController {
	
	@Autowired
	UploadService uploadService; 
	
	@PostMapping(path = "/file")
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile mf){
		uploadService.save(mf);
		return ResponseEntity.ok("Uploaded Successfully!!");
		
	}

}
