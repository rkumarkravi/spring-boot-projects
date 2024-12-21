package com.egjpa.rkproj.dto.res;

import com.egjpa.rkproj.dto.req.UserReqDto;
import com.egjpa.rkproj.models.Post;
import com.egjpa.rkproj.models.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserResDto extends UserReqDto {
    private Set<Post> createdPosts;
    private Set<Post> savedPosts;
    private Set<Post> postsLiked;

    public UserResDto(User user) {
        this.setId(user.getId());
        this.setBio(user.getBio());
        this.setFullName(user.getFullName());
        this.setUsername(user.getUsername());
        this.setEmail(user.getEmail());
        this.setProfilePictureUrl(user.getProfilePictureUrl());
        this.setCreatedPosts(user.getCreatedPosts());
        this.setPostsLiked(user.getPostsLiked());
        this.setSavedPosts(user.getSavedPosts());
    }
}
