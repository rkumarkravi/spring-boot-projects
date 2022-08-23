package com.rk.animestream.mapper;

import com.rk.animestream.dtos.MyListDto;
import com.rk.animestream.models.MyList;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface MyListMapper {
    MyList myListDtoToMyList(MyListDto myListDto);

    MyListDto myListToMyListDto(MyList myList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    MyList updateMyListFromMyListDto(MyListDto myListDto, @MappingTarget MyList myList);
}
