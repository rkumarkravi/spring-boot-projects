package com.egjpa.rkproj.service;

import com.egjpa.rkproj.dto.req.CommentReqDto;
import com.egjpa.rkproj.dto.res.CommentResDto;
import com.egjpa.rkproj.dto.res.CommonResDto;
import com.egjpa.rkproj.dto.res.PostResDto;
import com.egjpa.rkproj.models.Comment;
import com.egjpa.rkproj.models.Post;
import com.egjpa.rkproj.models.User;
import com.egjpa.rkproj.repos.CommentRepository;
import com.egjpa.rkproj.repos.PostRepository;
import com.egjpa.rkproj.repos.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, ModelMapper modelMapper,
                          UserRepository userRepository,
                          PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public CommonResDto<?> addComment(CommentReqDto commentReqDto) {
        var commonResp = CommonResDto.<CommentResDto>builder()
                .rs("F");

        Optional<User> userOpt = userRepository.findById(commentReqDto.getUserId());
        Optional<Post> postOpt = postRepository.findById(commentReqDto.getId());
        if(userOpt.isPresent()){
            Comment comment=new Comment();
            comment.setUser(userOpt.get());
            if(postOpt.isPresent()) {
                comment.setPost(postOpt.get());
                comment.setContent(commentReqDto.getContent());

                comment=commentRepository.save(comment);
                commonResp.rs("S");
                commonResp.payload(this.modelMapper.map(comment,CommentResDto.class));
            }else{
                commonResp.rd("Invalid post id");
            }
        }else{
            commonResp.rd("User not found!");
        }
        return commonResp.build();
    }
    public CommonResDto<?> getAllComments(CommentReqDto commentReqDto) {
        var commonResp = CommonResDto.builder()
                .rs("S");
        Pageable pageable=null;
        if(commentReqDto.getPage()!=null) {
//            pageable = Pageable.ofSize(commentReqDto.getPage().getSize())
//                    .withPage(commentReqDto.getPage().getPage());
//            pageable.getSortOr(Sort.by(commentReqDto.getPage().getSortBy()));
            pageable=PageRequest.of(commentReqDto.getPage().getPage(),
                    commentReqDto.getPage().getSize(),
                    commentReqDto.getPage().getSortOrder().equals("ASC")? Sort.Direction.ASC: Sort.Direction.DESC,commentReqDto.getPage().getSortBy().split(","));
        }else {
            pageable=Pageable.unpaged();
        }
        var allComments = commentRepository.findByPost_IdOrderByCreatedAtAsc(commentReqDto.getId(), pageable);
        commonResp.payload(allComments.map(comment -> modelMapper.map(comment, CommentResDto.class)));
        return commonResp.build();
    }
}
