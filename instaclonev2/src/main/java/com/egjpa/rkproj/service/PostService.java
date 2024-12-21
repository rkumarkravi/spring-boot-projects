package com.egjpa.rkproj.service;

import com.egjpa.rkproj.dto.req.PostReqDto;
import com.egjpa.rkproj.dto.res.CommonResDto;
import com.egjpa.rkproj.dto.res.PostResDto;
import com.egjpa.rkproj.models.Post;
import com.egjpa.rkproj.models.User;
import com.egjpa.rkproj.repos.PostRepository;
import com.egjpa.rkproj.repos.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public PostService(PostRepository postRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }


    public CommonResDto<?> createPost(PostReqDto postReqDto) {
        var commonResp = CommonResDto.<PostResDto>builder()
                .rs("F");

        Post post = this.modelMapper.map(postReqDto, Post.class);
        Optional<User> userOpt = this.userRepository.findById(postReqDto.getUserId());

        if (userOpt.isPresent()) {
            post.setCreator(userOpt.get());
            post = postRepository.save(post);
            commonResp
                    .rs("S")
                    .payload(this.modelMapper.map(post, PostResDto.class));
        } else {
            commonResp.rd("User not found!");
        }

        return commonResp.build();
    }

}
