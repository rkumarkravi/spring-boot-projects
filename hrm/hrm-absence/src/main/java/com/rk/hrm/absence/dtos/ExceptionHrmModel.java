package com.rk.hrm.absence.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public class ExceptionHrmModel {
    String type;
    String status;
    String desc;
    String path;
    Date date;
}
