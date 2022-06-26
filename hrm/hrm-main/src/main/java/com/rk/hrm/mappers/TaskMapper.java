package com.rk.hrm.mappers;

import com.rk.hrm.dtos.TaskDto;
import com.rk.hrm.models.Task;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface TaskMapper {
    Task taskDtoToTask(TaskDto taskDto);

    TaskDto taskToTaskDto(Task task);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTaskFromTaskDto(TaskDto taskDto, @MappingTarget Task task);
}
