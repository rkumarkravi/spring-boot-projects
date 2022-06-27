package com.rk.hrm.absence.repos;

import com.rk.hrm.absence.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailIgnoreCase(String email);

    Set<User> findByReportingTo_Id(@NonNull Long id);

    Set<User> findByProjectNull();



}