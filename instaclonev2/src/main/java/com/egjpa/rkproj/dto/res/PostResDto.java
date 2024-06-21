package com.egjpa.rkproj.dto.res;

import com.egjpa.rkproj.models.Comment;
import com.egjpa.rkproj.models.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class PostResDto {
    private Long id;
    private String imageUrl;
    private String description;
    private Long userId;
    private CreatorInfo creator;
    private LocalDateTime createdOn;
    private Set<CreatorInfo> likedBy = new LinkedHashSet<>();

    @Getter
    @Setter
    @ToString
    @RequiredArgsConstructor
    @Builder
    @AllArgsConstructor
    private static class CreatorInfo {
        private Long id;
        private String username;
        private String profilePictureUrl;
    }
}
