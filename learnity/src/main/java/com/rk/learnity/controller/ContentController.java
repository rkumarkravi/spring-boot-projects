package com.rk.learnity.controller;

import com.rk.learnity.dto.request.ContentDto;
import com.rk.learnity.entity.CFile;
import com.rk.learnity.enums.ContentTypeLearnity;
import com.rk.learnity.enums.ResponseMessages;
import com.rk.learnity.service.ContentService;
import com.rk.learnity.utility.ResponseUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/content")
public class ContentController {

    @Autowired
    ContentService contentService;

    @PostMapping("/add/{topicId}")
    private ResponseEntity postContent(@PathVariable("topicId") String topicId, @RequestBody List<ContentDto> contentDto) throws Exception {
        Optional<List<ContentDto>> contentDtoOptional = contentService.addContentByTopicId(contentDto, topicId);
        if (contentDtoOptional.isPresent()) {
            return ResponseUtility.setSuccessResponse(contentDtoOptional.get());
        } else {
            return ResponseUtility.setFailureResponse(ResponseMessages.FFR.getValue(), HttpStatus.OK);
        }
    }

    @PostMapping("/upload")
    private ResponseEntity uploadContent(@RequestParam("file") MultipartFile mf) throws Exception {
        Optional<String> contentDtoOptional = contentService.uploadContentByTopicId(mf);
        if (contentDtoOptional.isPresent()) {
            return ResponseUtility.setSuccessResponse(contentDtoOptional.get());
        } else {
            return ResponseUtility.setFailureResponse(ResponseMessages.FFR.getValue(), HttpStatus.OK);
        }
    }

    @GetMapping("/download/{bid}")
    private ResponseEntity downloadContent(@PathVariable String bid) throws Exception {
        Optional<CFile> cFile = contentService.downloadContentByTopicId(bid);
        if (cFile.isPresent()) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + cFile.get().getFid() + "\"")
                    .body(cFile.get().getBlob());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found!!");
        }
    }

    @GetMapping("/get/{topicId}")
    private ResponseEntity postContent(@PathVariable("topicId") String topicId) throws Exception {
        Optional<List<ContentDto>> contentDtoOptional = contentService.getContentByTopicId(topicId);
        if (contentDtoOptional.isPresent()) {
            return ResponseUtility.setSuccessResponse(contentDtoOptional.get());
        } else {
            return ResponseUtility.setFailureResponse(ResponseMessages.FFR.getValue(), HttpStatus.OK);
        }
    }

}
