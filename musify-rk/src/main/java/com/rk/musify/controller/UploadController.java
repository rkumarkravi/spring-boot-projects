package com.rk.musify.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rk.musify.service.UploadService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/upload")
//@CrossOrigin(origins = "http://localhost:4200")
public class UploadController {

	@Value("${user.fileContentTypeExpected}")
	public String[] fileContentTypes;
	@Autowired
	UploadService uploadService;

	@PostMapping(path = "/files/{aid}")
	public ResponseEntity<?> uploadFile(@RequestParam("files") MultipartFile[] mfs, @PathVariable("aid") String aid) {
		String cntntTypeString = Arrays.toString(fileContentTypes);
		Integer uploaded = 0;
		for (MultipartFile mf : mfs) {
			log.info("files expected:{} , fileName: {}", cntntTypeString, mf.getContentType());
			String fileContentType = mf.getContentType();
			if (Arrays.asList(fileContentTypes).stream().anyMatch(x -> x.equals(fileContentType))) {
				uploadService.save(mf,aid);
				uploaded++;
			}
		}
		if (uploaded > 0)
			return ResponseEntity.ok("Uploaded Successfully!!");
		else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("content type expected is: " + cntntTypeString);
		}
	}

}
