package com.rk.hrm.services;

import com.rk.hrm.enums.Status;
import com.rk.hrm.enums.TaskTypes;
import com.rk.hrm.models.Task;
import com.rk.hrm.models.User;
import com.rk.hrm.repos.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserService userService;

    public List<Task> getAllTasksByAssignedToId(Long assignedToId, Integer page, Integer limit) {

        return taskRepository.findByAssignedTo_Id(assignedToId, PageRequest.of(page, limit, Sort.by("createdOn").descending()));
    }

    public Collection<Task> getAllTasksByAssignedForId(Long assignedFor, Integer page, Integer limit) {
        return taskRepository.findByAssignedFor_Id(assignedFor, PageRequest.of(page, limit, Sort.by("createdOn").descending()));
    }

    public int updateStatusById(Long id, Status status) {
        return taskRepository.updateStatusById(status, id);
    }

    public Task addTask(long uid, Map<String, String> map) {
        User user = userService.getUserById(uid);
        log.info("Adding task for user {}", uid);
        Task task = new Task();
        task.setTaskTitle(map.get("taskTitle"));
        task.setTaskDesc(map.get("taskDesc"));
        task.setCreatedOn(new Date());
        task.setTaskTypes(map.get("taskTypes") != null ? TaskTypes.valueOf(map.get("taskTypes")) : TaskTypes.NOTIFICATION);
        task.setStatus(Status.PENDING);
        task.setAssignedFor(user);
        if (user.getReportingTo() != null) {
            task.setAssignedTo(user.getReportingTo());
        }
        return taskRepository.save(task);
    }
}
