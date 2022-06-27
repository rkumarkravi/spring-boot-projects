package com.rk.hrm.absence.mappers;

import com.rk.hrm.absence.dtos.TaskDto;
import com.rk.hrm.absence.models.Task;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface TaskMapper {
    Task taskDtoToTask(TaskDto taskDto);

    TaskDto taskToTaskDto(Task task);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTaskFromTaskDto(TaskDto taskDto, @MappingTarget Task task);
}
