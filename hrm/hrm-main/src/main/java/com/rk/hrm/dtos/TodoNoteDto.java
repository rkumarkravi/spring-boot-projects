package com.rk.hrm.dtos;

import com.rk.hrm.enums.PRIORITY;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoNoteDto implements Serializable {
    private Long id;
    private PRIORITY priority;
    private String desc;
    private Date added;
}
