package com.rk.learnity.controller;

import com.rk.learnity.dto.request.ContentDto;
import com.rk.learnity.enums.ResponseMessages;
import com.rk.learnity.service.ContentService;
import com.rk.learnity.utility.ResponseUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
