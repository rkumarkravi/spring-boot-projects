package com.rk.learnity.controller;

import com.rk.learnity.dto.request.TopicDto;
import com.rk.learnity.dto.response.MasterResponseDto;
import com.rk.learnity.enums.ResponseMessages;
import com.rk.learnity.service.TopicService;
import com.rk.learnity.utility.ResponseUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/topics")
@Slf4j
public class TopicController {
    @Autowired
    TopicService topicService;

    @ModelAttribute("realIp")
    public String getIp(HttpServletRequest httpServletRequest) {
        String realIp = httpServletRequest.getRemoteAddr();
        return realIp;
    }

    @GetMapping("/{courseId}")
    private ResponseEntity<MasterResponseDto<List<TopicDto>>> getTopics(@PathVariable("courseId") String courseId) {
        Long courseIdLong = Long.parseLong(courseId);
        Optional<List<TopicDto>> optionalTopics = Optional.ofNullable(topicService.getTopics(courseIdLong));
        if (optionalTopics.isPresent()) {
            return ResponseUtility.setSuccessResponse(optionalTopics.get());
        } else {
            return ResponseUtility.setFailureResponse(ResponseMessages.FFR.getValue(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add/{courseId}")
    private ResponseEntity<MasterResponseDto<List<TopicDto>>> addTopics(@PathVariable("courseId") Long courseId, @RequestBody List<TopicDto> topics) {
        return ResponseUtility.setSuccessResponse(topicService.addTopics(courseId, topics));
    }
}
