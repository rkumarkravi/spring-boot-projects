package com.rk.learnity.entity;

import com.rk.learnity.consts.TableNameConfig;
import com.rk.learnity.enums.ContentTypeLearnity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = TableNameConfig.CONTENT)
@Data
@NoArgsConstructor
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long contentId;
    @Enumerated(EnumType.STRING)
    @Column(name = "content_type")
    ContentTypeLearnity type;
    @Lob
    @Column(name = "content_value")
    String value;
    @Column(name = "content_order")
    int order;

    @ManyToOne
    @JoinColumn(name = "topic_topic_id")
    private Topic topic;

    @ManyToOne
    @JoinColumn(name = "sub_topic_sub_topic_id")
    private SubTopic subTopic;
}
