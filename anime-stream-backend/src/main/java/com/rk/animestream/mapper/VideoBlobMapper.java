package com.rk.animestream.mapper;

import com.rk.animestream.dtos.VideoBlobDto;
import com.rk.animestream.models.VideoBlob;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface VideoBlobMapper {
    VideoBlob videoBlobDtoToVideoBlob(VideoBlobDto videoBlobDto);

    VideoBlobDto videoBlobToVideoBlobDto(VideoBlob videoBlob);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    VideoBlob updateVideoBlobFromVideoBlobDto(VideoBlobDto videoBlobDto, @MappingTarget VideoBlob videoBlob);
}
