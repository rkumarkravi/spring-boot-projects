package com.rk.learnity.entity;

import com.rk.learnity.consts.TableNameConfig;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = TableNameConfig.COURSE)
@Data
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long courseId;
    String title;
    String descp;
    String tags;
}
