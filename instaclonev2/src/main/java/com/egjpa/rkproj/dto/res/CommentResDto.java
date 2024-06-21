package com.egjpa.rkproj.dto.res;

import com.egjpa.rkproj.models.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class CommentResDto {
    private Long id;
    private String content;
    private CommenterInfo user;
    private LocalDateTime createdAt;

    @Getter
    @Setter
    @ToString
    @RequiredArgsConstructor
    @Builder
    @AllArgsConstructor
    private static class CommenterInfo {
        private Long id;
        private String username;
        private String profilePictureUrl;
    }
}
