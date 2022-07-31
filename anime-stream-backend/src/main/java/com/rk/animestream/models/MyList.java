package com.rk.animestream.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "my_list")
@Data
@NoArgsConstructor
public class MyList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "mid", nullable = false)
    private Long mId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_uid", nullable = false, unique = true)
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date addedOn=new Date();
}