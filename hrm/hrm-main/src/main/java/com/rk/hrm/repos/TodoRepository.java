package com.rk.hrm.repos;

import com.rk.hrm.models.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    @Query("select t from Todo t where t.user.id = ?1")
    List<Todo> findByUser_Id(Long id);
}