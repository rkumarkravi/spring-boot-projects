package com.rk.animestream.dtos;

import com.rk.animestream.enums.AnimeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimeDto implements Serializable {
    private Long aId;
    private AnimeType animeType;
    private String name;
    private String dateOfRelease;
    private String credits;
    private String description;
    private String animeBackdrop;
    private String poster;
    private String originCountry;
    private Integer likes;
    private String trailerLink;
    private Set<VideoDto> videos = new LinkedHashSet<>();
}
