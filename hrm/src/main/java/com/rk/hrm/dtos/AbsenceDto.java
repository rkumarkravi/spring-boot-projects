package com.rk.hrm.dtos;

import com.rk.hrm.enums.AbsenceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AbsenceDto implements Serializable {
    private Long id;
    private AbsenceType absenceType;
    private String leaveReason;
    private Date from;
    private Date to;
    private String comments;
}
