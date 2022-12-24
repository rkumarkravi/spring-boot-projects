package com.rk.learnity.controller;

import com.rk.learnity.dto.response.CourseDto;
import com.rk.learnity.dto.response.MasterResponseDto;
import com.rk.learnity.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    CourseService courseService;

    @GetMapping("/")
    ResponseEntity<MasterResponseDto<List<CourseDto>>> getTopics() {
        return new ResponseEntity<>(courseService.getLatestTopics(), HttpStatus.OK);
    }
}
