package com.rk.animestream.service;

import com.rk.animestream.daos.AnimeRepository;
import com.rk.animestream.dtos.AnimeDto;
import com.rk.animestream.dtos.AnimeSearchDto;
import com.rk.animestream.mapper.AnimeMapper;
import com.rk.animestream.mapper.AnimeSearchMapper;
import com.rk.animestream.models.Anime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AnimeService {

    @Autowired
    AnimeRepository animeRepository;

    @Autowired
    AnimeMapper animeMapper;

    @Autowired
    AnimeSearchMapper animeSearchMapper;

    public AnimeDto addAnime(AnimeDto animeDto){
       return animeMapper.animeToAnimeDto(animeRepository.save(animeMapper.animeDtoToAnime(animeDto)));
    }

    public AnimeDto getAnime(Long aid){
        return animeMapper.animeToAnimeDto(animeRepository.getReferenceById(aid));
    }

    public AnimeDto getAnime(Long aid,Long seasonNo){
        Optional<Anime> anime = animeRepository.getAnimeByIdAndSeasonNo(aid, seasonNo);
        if(anime.isPresent()){
          return  animeMapper.animeToAnimeDto(anime.get());
        }else{
            throw new NoResultException("No Anime with id and season available!!");
        }
    }

    public PageImpl<AnimeDto> getAllAnime(Pageable pageable){
        Page<Anime> recentAdded = this.animeRepository.findAll(pageable);
        return new PageImpl<>(recentAdded.getContent().stream().filter(x->x.getReleased()==true).map(anime->animeMapper.animeToAnimeDto(anime)).collect(Collectors.toList()));
    }

    public Set<AnimeDto> getForReleaseCalender(Date d){
        return this.animeRepository.findDistinctByDateOfReleaseOrderByNameAsc(d).stream().map(x->animeMapper.animeToAnimeDto(x)).collect(Collectors.toSet());
    }

    public Set<AnimeDto> search(String searchText){
        return this.animeRepository.findByNameLikeOrderByNameAsc(searchText).parallelStream().map((x)->animeMapper.animeToAnimeDto(x)).collect(Collectors.toSet());
    }

}
