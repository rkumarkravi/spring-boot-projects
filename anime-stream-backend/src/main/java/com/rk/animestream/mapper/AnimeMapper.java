package com.rk.animestream.mapper;

import com.rk.animestream.dtos.AnimeDto;
import com.rk.animestream.models.Anime;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface AnimeMapper {
    Anime animeDtoToAnime(AnimeDto animeDto);

    AnimeDto animeToAnimeDto(Anime anime);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Anime updateAnimeFromAnimeDto(AnimeDto animeDto, @MappingTarget Anime anime);
}
