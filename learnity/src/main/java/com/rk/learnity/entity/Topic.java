package com.rk.learnity.entity;

import com.rk.learnity.consts.TableNameConfig;
import lombok.Data;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = TableNameConfig.TOPIC)
@Data
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "topic_id", nullable = false)
    Long topicId;
    String title;

    @ManyToOne
    @JoinColumn(name = "course_course_id")
    private Course course;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "topic_topic_id")
    private Set<SubTopic> subTopics = new LinkedHashSet<>();
}
