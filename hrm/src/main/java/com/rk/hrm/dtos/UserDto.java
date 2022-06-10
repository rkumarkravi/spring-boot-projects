package com.rk.hrm.dtos;

import com.rk.hrm.enums.GENDER;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
public class UserDto implements Serializable {
    private Long id;
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String role;
    private String status;
    private String designation;
    private Date joiningDate;
    private Date dob;
    private GENDER gender;
    private Date created_at;
    private DepartmentDto department;
    private UserDto reportingTo;
    private ProjectDto project;
}
