package com.rk.hrm.repos;

import com.rk.hrm.models.Leave;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveRepository extends JpaRepository<Leave, Long> {
}