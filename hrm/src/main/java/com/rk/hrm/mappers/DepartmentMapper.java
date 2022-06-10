package com.rk.hrm.mappers;

import com.rk.hrm.dtos.DepartmentDto;
import com.rk.hrm.models.Department;
import org.mapstruct.*;
import org.springframework.stereotype.Component;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
@Component
public interface DepartmentMapper {
    Department departmentDtoToDepartment(DepartmentDto departmentDto);

    DepartmentDto departmentToDepartmentDto(Department department);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDepartmentFromDepartmentDto(DepartmentDto departmentDto, @MappingTarget Department department);
}
