package com.rk.animestream.mapper;

import com.rk.animestream.dtos.VideoDto;
import com.rk.animestream.models.Video;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface VideoMapper {
    Video videoDtoToVideo(VideoDto videoDto);

    VideoDto videoToVideoDto(Video video);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Video updateVideoFromVideoDto(VideoDto videoDto, @MappingTarget Video video);
}
