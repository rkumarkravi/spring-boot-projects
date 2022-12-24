package com.rk.learnity.dao;

import com.rk.learnity.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentDao extends JpaRepository<Content, Long> {
    @Query("select c from Content c where c.topic.topicId = ?1 order by c.order")
    List<Content> findByTopic_TopicIdOrderByOrderAsc(Long topicId);
}