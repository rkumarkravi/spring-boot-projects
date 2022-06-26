package com.rk.hrm.repos;

import com.rk.hrm.models.Absence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AbsenceRepository extends JpaRepository<Absence, Long> {
    @Query("select a from Absence a where a.user.id = ?1 and a.fromDate = ?2 and a.toDate = ?3")
    Optional<Absence> findByUser_IdAndFromAndTo(Long id, Date from, Date to);

    @Query("select a from Absence a where a.user.id = ?1 and a.fromDate between ?2 and ?3")
    List<Absence> findByUser_IdAndFromDateBetween(Long id, Date fromDateStart, Date fromDateEnd);


}