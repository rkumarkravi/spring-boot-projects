package com.rk.hrm.controllers;

import com.rk.hrm.dtos.TodoDto;
import com.rk.hrm.dtos.TodoNoteDto;
import com.rk.hrm.services.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todo")
public class TodoController {
    @Autowired
    TodoService todoService;

    @PostMapping("/add/{uid}")
    public TodoDto addTodo(@PathVariable("uid") long uid) {
        return todoService.addTodo(uid);
    }

    @GetMapping("get-all/{uid}")
    public List<TodoDto> getAllTodo(@PathVariable("uid") long uid) {
        return todoService.getTodos(uid);
    }

    @PostMapping("insert/todo-note/{todoId}")
    public TodoDto insertTodoNote(@PathVariable("todoId") long todoId, @RequestBody TodoNoteDto todoNoteDto) {
        return todoService.insertTodoNote(todoId, todoNoteDto);
    }

    @PatchMapping("update/todo-note/{todoNoteId}")
    public TodoNoteDto updateTodoNote(@PathVariable("todoNoteId") long todoNoteId, @RequestBody TodoNoteDto todoNoteDto) {
        return todoService.updateTodoNote(todoNoteId, todoNoteDto);
    }
}
