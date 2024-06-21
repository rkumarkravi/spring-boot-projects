package com.egjpa.rkproj.controllers;

import com.egjpa.rkproj.dto.req.PaginationDto;
import com.egjpa.rkproj.dto.req.PostReqDto;
import com.egjpa.rkproj.dto.req.UserReqDto;
import com.egjpa.rkproj.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody UserReqDto reqDto) {
        return new ResponseEntity<>(userService.createUser(reqDto), HttpStatus.OK);
    }

    @PostMapping("/getByUId")
    public ResponseEntity<?> getAllPosts(@RequestBody PostReqDto reqDto) {
        return new ResponseEntity<>(userService.getAllPosts(reqDto), HttpStatus.OK);
    }

    @PostMapping("/like")
    public ResponseEntity<?> likeAPost(@RequestBody PostReqDto reqDto) {
        return new ResponseEntity<>(userService.likeAPost(reqDto), HttpStatus.OK);
    }

    @PostMapping("/feeds")
    public ResponseEntity<?> feeds(@RequestBody PaginationDto reqDto) {
        return new ResponseEntity<>(userService.getGenericFeedPosts(reqDto), HttpStatus.OK);
    }
}
