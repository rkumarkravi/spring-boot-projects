package com.rk.learnity.service;

import com.rk.learnity.dao.CourseDao;
import com.rk.learnity.dao.TopicDao;
import com.rk.learnity.dto.response.CourseDto;
import com.rk.learnity.dto.response.MasterResponseDto;
import com.rk.learnity.entity.Course;
import com.rk.learnity.entity.Topic;
import com.rk.learnity.enums.ResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CourseService {

    @Autowired
    CourseDao courseDao;

    @Autowired
    TopicDao topicDao;

    public MasterResponseDto<List<CourseDto>> getLatestTopics() {
        List<Course> courses = courseDao.findAll().stream().limit(5).collect(Collectors.toList());
        Optional<List<Course>> topicsOptional = Optional.ofNullable(courses);
        MasterResponseDto<List<CourseDto>> masterResponseDto = new MasterResponseDto<>();

        log.info("data from db size is {}", topicsOptional.get().size());

        if (topicsOptional.isPresent() && topicsOptional.get().size() > 0) {
            courses = topicsOptional.get();
            List<CourseDto> courseDtos = courses.stream().map(c -> {
                CourseDto courseDto = new CourseDto(c);
                List<Topic> topics = topicDao.findByCourse_CourseId(c.getCourseId());
                courseDto.setTopicCount(topics.size());
                return courseDto;
            }).collect(Collectors.toList());
            masterResponseDto.setPayload(courseDtos);
            masterResponseDto.setRc("00");
            masterResponseDto.setRd("Data fetched successfully!");
            masterResponseDto.setRs(ResponseStatus.SUCCESS);
        } else {
            masterResponseDto.setRs(ResponseStatus.FAIL);
        }
        return masterResponseDto;
    }
}
