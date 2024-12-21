package com.egjpa.rkproj.controllers;

import com.egjpa.rkproj.dto.req.CommentReqDto;
import com.egjpa.rkproj.dto.req.PostReqDto;
import com.egjpa.rkproj.service.CommentService;
import com.egjpa.rkproj.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final CommentService commentService;

    public PostController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createPost(@RequestBody PostReqDto reqDto) {
        return new ResponseEntity<>(postService.createPost(reqDto), HttpStatus.OK);
    }

    @PostMapping("/comments")
    public ResponseEntity<?> getAllCommentsOfPost(@RequestBody CommentReqDto reqDto) {
        return new ResponseEntity<>(commentService.getAllComments(reqDto), HttpStatus.OK);
    }
}
