package com.rk.animestream.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "plans")
@NoArgsConstructor
@Data
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pid", nullable = false)
    private Long pid;
    private String price;
    private Integer devicesAllowed;
    private Integer months;
}
