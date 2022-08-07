package com.rk.animestream.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoDto implements Serializable {
    private Long vId;
    private String totalTime;
    private String title;
    private VideoBlobFileDto videoBlobFile;
    private ThumbnailDto thumbnail;
}
