package com.rk.hrm.repos;

import com.rk.hrm.models.TodoNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoNoteRepository extends JpaRepository<TodoNote, Long> {
}