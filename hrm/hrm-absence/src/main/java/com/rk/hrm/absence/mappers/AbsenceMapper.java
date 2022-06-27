package com.rk.hrm.absence.mappers;

import com.rk.hrm.absence.dtos.AbsenceDto;
import com.rk.hrm.absence.models.Absence;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface AbsenceMapper {
    Absence absenceDtoToAbsence(AbsenceDto absenceDto);

    AbsenceDto absenceToAbsenceDto(Absence absence);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateAbsenceFromAbsenceDto(AbsenceDto absenceDto, @MappingTarget Absence absence);
}
