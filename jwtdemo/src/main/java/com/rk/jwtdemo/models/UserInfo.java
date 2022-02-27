package com.rk.jwtdemo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rk.jwtdemo.enums.Gender;
import com.rk.jwtdemo.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "email",columnNames = "email"))
@JsonIgnoreProperties(value = {"password","otp"},allowSetters = true)
public class UserInfo {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   Long userid;
   String firstName;
   String lastName;
   String email;
   String password;
   @Temporal(TemporalType.DATE)
   Date dob;
   @Enumerated(EnumType.STRING)
   Gender gender;
   @Enumerated(EnumType.STRING)
   Status active;
   String otp;
}
