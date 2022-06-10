package com.rk.hrm.mappers;

import com.rk.hrm.dtos.ProjectDto;
import com.rk.hrm.models.Project;
import org.mapstruct.*;
import org.springframework.stereotype.Component;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
@Component
public interface ProjectMapper {
    Project projectDtoToProject(ProjectDto projectDto);

    ProjectDto projectToProjectDto(Project project);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProjectFromProjectDto(ProjectDto projectDto, @MappingTarget Project project);
}
