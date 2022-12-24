package com.rk.learnity.dto.request;

import com.rk.learnity.entity.Topic;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A DTO for the {@link com.rk.learnity.entity.Topic} entity
 */
@Data
@NoArgsConstructor
public class TopicDto implements Serializable {
    Long id;
    private String title;
    private Set<SubTopicDto> subTopics;

    public TopicDto(Topic topic) {
        this.id=topic.getTopicId();
        this.title = topic.getTitle();
        this.subTopics = topic.getSubTopics().parallelStream().map(st -> new SubTopicDto(st)).collect(Collectors.toSet());
    }

    public static List<TopicDto> getTopicDtoFromEntity(List<Topic> topics) {
        return topics.stream().map(t -> new TopicDto(t)).collect(Collectors.toList());
    }
}