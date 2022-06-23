package com.rk.hrm.mappers;

import com.rk.hrm.dtos.AbsenceDto;
import com.rk.hrm.models.Absence;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface AbsenceMapper {
    Absence absenceDtoToAbsence(AbsenceDto absenceDto);

    AbsenceDto absenceToAbsenceDto(Absence absence);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateAbsenceFromAbsenceDto(AbsenceDto absenceDto, @MappingTarget Absence absence);
}
