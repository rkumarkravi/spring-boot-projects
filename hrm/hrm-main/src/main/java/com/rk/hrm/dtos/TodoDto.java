package com.rk.hrm.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class TodoDto implements Serializable {
    private Long id;
    private Date date;
    private List<TodoNoteDto> todoNotes;
}
