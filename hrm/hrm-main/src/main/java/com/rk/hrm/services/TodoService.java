package com.rk.hrm.services;

import com.rk.hrm.dtos.TodoDto;
import com.rk.hrm.dtos.TodoNoteDto;
import com.rk.hrm.exceptions.ResourceNotFoundException;
import com.rk.hrm.mappers.TodoMapper;
import com.rk.hrm.mappers.TodoNoteMapper;
import com.rk.hrm.models.Todo;
import com.rk.hrm.models.TodoNote;
import com.rk.hrm.repos.TodoNoteRepository;
import com.rk.hrm.repos.TodoRepository;
import com.rk.hrm.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
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

    public TodoDto insertTodoNote(long todoId, TodoNoteDto todoNoteDto) throws ResourceNotFoundException {
        TodoNote todoNote = todoNoteMapper.todoNoteDtoToTodoNote(todoNoteDto);
        Todo insertForTodo = todoRepository.findById(todoId).orElseThrow(() -> new ResourceNotFoundException("Todo Not found for " + todoId));
        todoNote.setTodo(insertForTodo);
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

    public TodoDto moveTodoNote(long toTodoId, long todoNoteId) {
        Optional<Todo> toTodoOptional = todoRepository.findById(toTodoId);
        Optional<TodoNote> todoNoteOptional = todoNoteRepository.findById(todoNoteId);
        if (toTodoOptional.isPresent() && todoNoteOptional.isPresent()) {
            TodoNote toMoveNode = todoNoteOptional.get();
            toMoveNode.setTodo(toTodoOptional.get());
            todoNoteRepository.save(toMoveNode);
        }
        return todoMapper.todoToTodoDto(todoRepository.findById(toTodoId).get());
    }

    public Map deleteTodo(long todoId) {
        todoRepository.delete(todoRepository.findById(todoId).get());
        Map map = new HashMap();
        map.put("msg", "DELETED Todo");
        return map;
    }

    public Map deleteTodoNote(long todoNodeId) {
        todoNoteRepository.delete(todoNoteRepository.findById(todoNodeId).get());
        Map map = new HashMap();
        map.put("msg", "DELETED Todo Note");
        return map;
    }
}
