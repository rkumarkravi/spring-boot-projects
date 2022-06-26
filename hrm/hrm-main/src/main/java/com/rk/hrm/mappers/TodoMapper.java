package com.rk.hrm.mappers;

import com.rk.hrm.dtos.TodoDto;
import com.rk.hrm.models.Todo;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface TodoMapper {
    Todo todoDtoToTodo(TodoDto todoDto);

    TodoDto todoToTodoDto(Todo todo);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTodoFromTodoDto(TodoDto todoDto, @MappingTarget Todo todo);

    @AfterMapping
    default void linkTodoNotes(@MappingTarget Todo todo) {
        todo.getTodoNotes().forEach(todoNote -> todoNote.setTodo(todo));
    }
}
