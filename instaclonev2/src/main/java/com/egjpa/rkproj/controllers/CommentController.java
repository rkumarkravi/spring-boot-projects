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
@RequestMapping("/comments")
public class CommentController {
    private final PostService postService;
    private final CommentService commentService;

    public CommentController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> addComment(@RequestBody CommentReqDto reqDto) {
        return new ResponseEntity<>(commentService.addComment(reqDto), HttpStatus.OK);
    }

}
