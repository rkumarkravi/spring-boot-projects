package com.rk.hrm.services;

import com.rk.hrm.dtos.TodoDto;
import com.rk.hrm.dtos.TodoNoteDto;
import com.rk.hrm.mappers.TodoMapper;
import com.rk.hrm.mappers.TodoNoteMapper;
import com.rk.hrm.models.Todo;
import com.rk.hrm.models.TodoNote;
import com.rk.hrm.repos.TodoNoteRepository;
import com.rk.hrm.repos.TodoRepository;
import com.rk.hrm.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TodoService {
    @Autowired
    TodoRepository todoRepository;

    @Autowired
    TodoNoteRepository todoNoteRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TodoMapper todoMapper;

    @Autowired
    TodoNoteMapper todoNoteMapper;

    public TodoDto addTodo(long uid) {
        Todo todo = Todo.builder().date(new Date()).build();
        todo.setUser(userRepository.findById(uid).get());
        return todoMapper.todoToTodoDto(todoRepository.save(todo));
    }

    public List<TodoDto> getTodos(long uid) {
        return todoRepository.findByUser_Id(uid).stream().map(x -> todoMapper.todoToTodoDto(x)).collect(Collectors.toList());
    }

    public TodoDto insertTodoNote(long todoId, TodoNoteDto todoNoteDto) {
        TodoNote todoNote = todoNoteMapper.todoNoteDtoToTodoNote(todoNoteDto);
        todoNote.setTodo(todoRepository.findById(todoId).get());
        todoNote.setAdded(new Date());
        todoNoteRepository.save(todoNote);
        return todoMapper.todoToTodoDto(todoRepository.findById(todoId).get());
    }

    public TodoNoteDto updateTodoNote(long todoNoteId, TodoNoteDto todoNoteDto) {
        Optional<TodoNote> updateTodoNoteOptional = todoNoteRepository.findById(todoNoteId);
        if (updateTodoNoteOptional.isPresent()) {
            TodoNote updateTodoNote = updateTodoNoteOptional.get();
            TodoNote todoNote = todoNoteMapper.todoNoteDtoToTodoNote(todoNoteDto);
            updateTodoNote.setDesc(todoNote.getDesc());
            updateTodoNote.setPriority(todoNote.getPriority());
            updateTodoNote.setAdded(new Date());
            todoNoteRepository.save(updateTodoNote);
        }
        return todoNoteMapper.todoNoteToTodoNoteDto(todoNoteRepository.findById(todoNoteId).get());
    }

    public TodoDto updateTodoNote(long toTodoId, long todoNoteId) {
        Optional<Todo> toTodoOptional = todoRepository.findById(toTodoId);
        Optional<TodoNote> todoNoteOptional = todoNoteRepository.findById(todoNoteId);
        if (toTodoOptional.isPresent() && todoNoteOptional.isPresent()) {
            TodoNote toMoveNode = todoNoteOptional.get();
            toMoveNode.setTodo(toTodoOptional.get());
            todoNoteRepository.save(toMoveNode);
        }
        return todoMapper.todoToTodoDto(todoRepository.findById(toTodoId).get());
    }
}
