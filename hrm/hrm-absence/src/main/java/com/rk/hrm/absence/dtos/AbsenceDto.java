package com.rk.hrm.absence.dtos;

import com.rk.hrm.absence.enums.AbsenceType;
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
    private Date fromDate;
    private Date toDate;
    private String comments;
}
