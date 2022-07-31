package com.rk.animestream.daos;

import com.rk.animestream.models.UserPlans;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPlansRepository extends JpaRepository<UserPlans, Long> {
}