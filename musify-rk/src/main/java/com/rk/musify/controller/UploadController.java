package com.rk.musify.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rk.musify.service.UploadService;
import com.rk.musify.util.Util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/upload")
public class UploadController {

	@Value("${user.fileContentTypeExpected}")
	public String[] fileContentTypes;
	@Autowired
	UploadService uploadService;

	@PostMapping(path = "/file")
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile mf) {
		String cntntTypeString=Arrays.toString(fileContentTypes);
		log.info("files expected:{} , fileName: {}",cntntTypeString,mf.getContentType());
		String fileContentType = mf.getContentType();
		if (Arrays.asList(fileContentTypes).stream().anyMatch(x -> x.equals(fileContentType))) {
			uploadService.save(mf);
			return ResponseEntity.ok("Uploaded Successfully!!");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("content type expected is: "+cntntTypeString);
		}
	}

}
