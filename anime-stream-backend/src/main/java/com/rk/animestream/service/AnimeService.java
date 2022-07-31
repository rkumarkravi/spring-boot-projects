package com.rk.animestream.service;

import com.rk.animestream.daos.AnimeRepository;
import com.rk.animestream.dtos.AnimeDto;
import com.rk.animestream.mapper.AnimeMapper;
import com.rk.animestream.models.Anime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class AnimeService {

    @Autowired
    AnimeRepository animeRepository;

    @Autowired
    AnimeMapper animeMapper;

    public AnimeDto addAnime(AnimeDto animeDto){
       return animeMapper.animeToAnimeDto(animeRepository.save(animeMapper.animeDtoToAnime(animeDto)));
    }

    public AnimeDto getAnime(Long aid){
        return animeMapper.animeToAnimeDto(animeRepository.getReferenceById(aid));
    }
    
    public PageImpl<AnimeDto> getAllAnime(Pageable pageable){
        Page<Anime> recentAdded = this.animeRepository.findAll(pageable);
        return new PageImpl<>(recentAdded.getContent().stream().map(anime->animeMapper.animeToAnimeDto(anime)).collect(Collectors.toList()));
    }
}
