package com.rk.learnity.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.rk.learnity.consts.TableNameConfig.SUB_TOPIC;

@Entity
@Table(name = SUB_TOPIC)
@Data
@NoArgsConstructor
public class SubTopic {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "sub_topic_id")
    private Long id;
    private String title;
}