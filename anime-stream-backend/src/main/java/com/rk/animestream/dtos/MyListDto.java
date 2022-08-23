package com.rk.animestream.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyListDto implements Serializable {
    private Long mId;
    private UserDto user;
    private Set<AnimeDto> animes = new LinkedHashSet<>();
    private Date addedOn = new Date();
}
