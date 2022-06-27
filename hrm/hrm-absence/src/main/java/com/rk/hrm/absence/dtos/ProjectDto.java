package com.rk.hrm.absence.dtos;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProjectDto implements Serializable {
    private Long id;
    private String name;
    private String description;
    private String status;
}
