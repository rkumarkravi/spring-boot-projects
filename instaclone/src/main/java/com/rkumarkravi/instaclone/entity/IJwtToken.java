package com.rkumarkravi.instaclone.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "i_jwt_token")
public class IJwtToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String deviceMode;
    private String authToken;
    private String refreshToken;

    @Column(name = "creation_date")
    private LocalDateTime creationDate=LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private IUser user;

}