package com.rk.hrm.dtos;

import com.rk.hrm.enums.Status;
import com.rk.hrm.enums.TaskTypes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto implements Serializable {
    private Long id;
    private String taskTitle;
    private String taskDesc;
    private Date createdOn;
    private TaskTypes taskTypes;
    private Status status;
    private UserDto assignedFor;
    private UserDto assignedTo;
}
