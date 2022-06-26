package com.rk.hrm.mappers;

import com.rk.hrm.dtos.TodoNoteDto;
import com.rk.hrm.models.TodoNote;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface TodoNoteMapper {
    TodoNote todoNoteDtoToTodoNote(TodoNoteDto todoNoteDto);

    TodoNoteDto todoNoteToTodoNoteDto(TodoNote todoNote);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTodoNoteFromTodoNoteDto(TodoNoteDto todoNoteDto, @MappingTarget TodoNote todoNote);
}
