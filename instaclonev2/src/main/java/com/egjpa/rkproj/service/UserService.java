package com.egjpa.rkproj.service;

import com.egjpa.rkproj.dto.req.PaginationDto;
import com.egjpa.rkproj.dto.req.PostReqDto;
import com.egjpa.rkproj.dto.req.UserReqDto;
import com.egjpa.rkproj.dto.res.CommonResDto;
import com.egjpa.rkproj.dto.res.PostResDto;
import com.egjpa.rkproj.dto.res.UserRegisterResDto;
import com.egjpa.rkproj.models.Post;
import com.egjpa.rkproj.models.User;
import com.egjpa.rkproj.repos.PostRepository;
import com.egjpa.rkproj.repos.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PostRepository postRepository;

    public UserService(UserRepository userRepository, ModelMapper modelMapper, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.postRepository = postRepository;
    }

    public CommonResDto<?> createUser(UserReqDto userReqDto) {
        var commonResp = CommonResDto.<UserRegisterResDto>builder()
                .rs("S");
        User user = this.modelMapper.map(userReqDto, User.class);
        user = userRepository.save(user);
        commonResp.payload(this.modelMapper.map(user, UserRegisterResDto.class));

        return commonResp.build();
    }

    public CommonResDto<?> likeAPost(PostReqDto postReqDto) {
        var commonResp = CommonResDto.<Boolean>builder()
                .payload(false)
                .rs("S");
        Optional<Post> postOpt = this.postRepository.findById(postReqDto.getId());

        if (postOpt.isPresent()) {
            Post post = postOpt.get();
            Optional<User> userOpt = this.userRepository.findById(postReqDto.getUserId());
            if (userOpt.isPresent()) {
                post.getLikedBy().add(userOpt.get());
                postRepository.save(post);
                commonResp.payload(true);
            }
        }
        return commonResp.build();
    }

    public CommonResDto<?> getAllPosts(PostReqDto postReqDto) {
        var commonResp = CommonResDto.<List<PostResDto>>builder()
                .rs("F");
        Optional<User> userOpt = this.userRepository.findById(postReqDto.getUserId());

        if (userOpt.isPresent()) {
            var post = postRepository.findByUser_IdOrderByCreatedOnDesc(postReqDto.getUserId(), Pageable.ofSize(10));
            var postsResp = post.stream()
                    .map(x -> this.modelMapper.map(x, PostResDto.class))
                    .collect(Collectors.toList());
            commonResp
                    .rs("S")
                    .payload(postsResp);
        } else {
            commonResp.rd("User not found!");
        }

        return commonResp.build();
    }

    public CommonResDto<?> getGenericFeedPosts(PaginationDto pageDto) {
        var commonResp = CommonResDto.builder()
                .rs("S");
        var pageable = PageRequest.of(pageDto.getPage(),
                pageDto.getSize(),
                pageDto.getSortOrder().equals("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC, pageDto.getSortBy().split(","));

        var posts = postRepository.findByOrderByCreatedOnDesc(pageable);

        commonResp
                .rs("S")
                .payload(posts.map(post -> this.modelMapper.map(post, PostResDto.class)));

        return commonResp.build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user=userRepository.findByUsernameOrEmail(username).orElse(null);
        System.out.println(user);
        return user;
    }
}
