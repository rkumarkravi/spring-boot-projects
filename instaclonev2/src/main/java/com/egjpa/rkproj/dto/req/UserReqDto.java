package com.egjpa.rkproj.dto.req;

import lombok.Data;

@Data
public class UserReqDto {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private String bio;
    private String profilePictureUrl;
}
