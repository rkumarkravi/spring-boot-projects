package com.rk.hrm.dtos;

import com.rk.hrm.enums.AbsenceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveDto implements Serializable {
    private Long id;
    private AbsenceType leaveType;
    private Integer balance;
}
