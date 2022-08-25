package com.rk.animestream.dtos;

import com.rk.animestream.enums.AnimeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimeSearchDto implements Serializable {
    private Long aId;
    private AnimeType animeType;
    private String name;
    private Date dateOfRelease;
    private String description;
    private String animeBackdrop;
    private String poster;
}
