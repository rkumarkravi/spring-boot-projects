package com.rk.animestream.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"password","otp"},allowSetters = true)
public class UserDto implements Serializable {
    private Long uId;
    private String firstname;
    private String lastname;
    private Date addedOn = new Date();
    private String emailId;
    private String mobileNo;
    private String password;
    private String otp;
}
