package com.rk.learnity.dao;

import com.rk.learnity.entity.SubTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubTopicDao extends JpaRepository<SubTopic, Long> {
}