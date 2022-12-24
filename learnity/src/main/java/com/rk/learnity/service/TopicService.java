package com.rk.learnity.service;

import com.rk.learnity.dao.CourseDao;
import com.rk.learnity.dao.TopicDao;
import com.rk.learnity.dto.request.SubTopicDto;
import com.rk.learnity.dto.request.TopicDto;
import com.rk.learnity.entity.Course;
import com.rk.learnity.entity.SubTopic;
import com.rk.learnity.entity.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TopicService {
    @Autowired
    TopicDao topicDao;
    @Autowired
    CourseDao courseDao;

    public List<TopicDto> getTopics(Long courseId) {
        return TopicDto.getTopicDtoFromEntity(topicDao.findByCourse_CourseId(courseId));
    }

    public List<TopicDto> addTopics(Long courseId, List<TopicDto> topicsToAdd) {
        List<TopicDto> added = new ArrayList<>();
        for (TopicDto topicDto : topicsToAdd) {
            Topic topic = new Topic();
            topic.setTitle(topicDto.getTitle());
            if (topicDto.getSubTopics() != null) {
                Set<SubTopic> subTopics = new LinkedHashSet<>();
                for (SubTopicDto subTopicDto : topicDto.getSubTopics()) {
                    SubTopic subTopic = new SubTopic();
                    subTopic.setTitle(subTopicDto.getTitle());
                    subTopics.add(subTopic);
                }
                topic.setSubTopics(subTopics);
            }
            Optional<Course> courseOptional = courseDao.findById(courseId);
            if (!courseOptional.isPresent()) {
                continue;
            }
            topic.setCourse(courseOptional.get());
            added.add(new TopicDto(topicDao.save(topic)));
        }
        return added;
    }
}
