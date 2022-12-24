package com.rk.learnity.dao;

import com.rk.learnity.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicDao extends JpaRepository<Topic, Long> {
    List<Topic> findByCourse_CourseId(Long courseId);
}