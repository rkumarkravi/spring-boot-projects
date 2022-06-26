package com.rk.hrm.controllers;

import com.rk.hrm.dtos.TaskDto;
import com.rk.hrm.enums.Status;
import com.rk.hrm.mappers.TaskMapper;
import com.rk.hrm.services.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/tasks")
@Slf4j
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskMapper taskMapper;

    @PostMapping("/{uid}")
    public TaskDto addTask(@PathVariable("uid") long uid, @RequestBody Map<String, String> metaData) {
        log.info("Adding task for user with id {}, data is {}", uid, metaData);
        return taskMapper.taskToTaskDto(taskService.addTask(uid, metaData));
    }

    @GetMapping(value = "/assigned-to/{assignedTo}/{pageSize}/{offset}")
    public List<TaskDto> getAllTasksAssignedToPageAble(@PathVariable("assignedTo") Long assignedTo, @PathVariable("pageSize") Integer pageSize, @PathVariable("offset") Integer offset) {
        return taskService.getAllTasksByAssignedToId(assignedTo, pageSize, offset).stream().map(x -> taskMapper.taskToTaskDto(x)).collect(java.util.stream.Collectors.toList());
    }

    @GetMapping(value = "/assigned-for/{assignedFor}/{pageSize}/{offset}")
    public List<TaskDto> getAllTasksAssignedForPageAble(@PathVariable("assignedFor") Long assignedFor, @PathVariable("pageSize") Integer pageSize, @PathVariable("offset") Integer offset) {
        return taskService.getAllTasksByAssignedForId(assignedFor, pageSize, offset).stream().map(x -> taskMapper.taskToTaskDto(x)).collect(java.util.stream.Collectors.toList());
    }

    @PutMapping(value = "/{taskId}/status")
    public ResponseEntity<Map> getAllTasksAssignedForPageAble(@PathVariable("taskId") Long taskId, @RequestParam("status") Status status) {
        Map<String, Object> map = new HashMap<>();
        if (taskService.updateStatusById(taskId, status) > 1) {
            map.put("status", true);
            map.put("message", "Task status updated successfully");
        } else {
            map.put("status", false);
            map.put("message", "Task status update failed");
        }
        return ResponseEntity.ok(map);
    }
}
