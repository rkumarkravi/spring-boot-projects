package com.rk.learnity.dto.request;

import com.rk.learnity.entity.SubTopic;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * A DTO for the {@link com.rk.learnity.entity.SubTopic} entity
 */
@Data
@NoArgsConstructor
public class SubTopicDto implements Serializable {
    private Long id;
    private String title;

    public SubTopicDto(SubTopic subTopic) {
        this.id=subTopic.getId();
        this.title = subTopic.getTitle();
    }
}