package com.rk.hrm.repos;

import com.rk.hrm.models.Absence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AbsenceRepository extends JpaRepository<Absence, Long> {
    @Query("select a from Absence a where a.user.id = ?1 and a.from = ?2 and a.to = ?3")
    Optional<Absence> findByUser_IdAndFromAndTo(Long id, Date from, Date to);

    @Query("select a from Absence a where a.from between ?1 and ?2")
    List<Absence> findByFromBetween(Date fromStart, Date fromEnd);


}