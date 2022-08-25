package com.rk.animestream.mapper;

import com.rk.animestream.dtos.AnimeSearchDto;
import com.rk.animestream.models.Anime;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface AnimeSearchMapper {
    Anime animeSearchDtoToAnime(AnimeSearchDto animeSearchDto);

    AnimeSearchDto animeToAnimeSearchDto(Anime anime);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Anime updateAnimeFromAnimeSearchDto(AnimeSearchDto animeSearchDto, @MappingTarget Anime anime);
}
