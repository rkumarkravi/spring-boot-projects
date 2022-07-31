package com.rk.animestream.mapper;

import com.rk.animestream.dtos.ThumbnailDto;
import com.rk.animestream.models.Thumbnail;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ThumbnailMapper {
    Thumbnail thumbnailDtoToThumbnail(ThumbnailDto thumbnailDto);

    ThumbnailDto thumbnailToThumbnailDto(Thumbnail thumbnail);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Thumbnail updateThumbnailFromThumbnailDto(ThumbnailDto thumbnailDto, @MappingTarget Thumbnail thumbnail);
}
