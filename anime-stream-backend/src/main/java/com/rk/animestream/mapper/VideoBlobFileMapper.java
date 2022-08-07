package com.rk.animestream.mapper;

import com.rk.animestream.dtos.VideoBlobFileDto;
import com.rk.animestream.models.VideoBlobFile;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface VideoBlobFileMapper {
    VideoBlobFile videoBlobFileDtoToVideoBlobFile(VideoBlobFileDto videoBlobFileDto);

    VideoBlobFileDto videoBlobFileToVideoBlobFileDto(VideoBlobFile videoBlobFile);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    VideoBlobFile updateVideoBlobFileFromVideoBlobFileDto(VideoBlobFileDto videoBlobFileDto, @MappingTarget VideoBlobFile videoBlobFile);
}
