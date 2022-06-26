package com.rk.hrm.repos;

import com.rk.hrm.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    @Query("select d from Department d where upper(d.name) = upper(?1)")
    Optional<Department> findByNameIgnoreCase(String name);

}