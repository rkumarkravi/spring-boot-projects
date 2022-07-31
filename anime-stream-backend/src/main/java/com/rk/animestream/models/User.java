package com.rk.animestream.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", nullable = false)
    private Long uId;
    private String firstname;
    private String lastname;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date addedOn=new Date();
    @Column(nullable = false, unique = true)
    private String emailId;
    @Column(nullable = false, unique = true)
    private String mobileNo;
    @Column(nullable = false)
    private String password;
}