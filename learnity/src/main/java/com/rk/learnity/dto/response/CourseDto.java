package com.rk.learnity.dto.response;

import com.rk.learnity.entity.Course;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class CourseDto implements Serializable {
    private Long courseId;
    private String title;
    private String descp;
    private String tags;
    private Integer topicCount;
    private Integer completeCount;

    public CourseDto(Course course) {
        this.setCourseId(course.getCourseId());
        this.setTitle(course.getTitle());
        this.setDescp(course.getDescp());
        this.setTags(course.getTags());
    }
}
