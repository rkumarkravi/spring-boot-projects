package com.rk.hrm.repos;

import com.rk.hrm.enums.Status;
import com.rk.hrm.models.Task;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByAssignedTo_Id(@NonNull Long id, Pageable pageable);

    List<Task> findByAssignedFor_Id(@NonNull Long id, Pageable pageable);

    @Transactional
    @Modifying
    @Query("update Task t set t.status = ?1 where t.id = ?2")
    int updateStatusById(@NonNull Status status, @NonNull Long id);
}