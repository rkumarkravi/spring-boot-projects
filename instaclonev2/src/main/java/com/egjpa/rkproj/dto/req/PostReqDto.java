package com.egjpa.rkproj.dto.req;

import lombok.Data;

@Data
public class PostReqDto {
    private Long id;
    private String imageUrl;
    private String description;
    private Long userId;// TO-DO: use this while auth is not implemented
}
