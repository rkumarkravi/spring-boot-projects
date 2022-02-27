package com.rk.jwtdemo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class UserInfo {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   Long userid;
   String firstName;
   String lastName;
   String email;
   @JsonIgnore
   String password;
   @Temporal(TemporalType.DATE)
   Date dob;
   @Enumerated(EnumType.STRING)
   Gender gender;
   @Enumerated(EnumType.STRING)
   Status active;
   @JsonIgnore
   String OTP;
}
