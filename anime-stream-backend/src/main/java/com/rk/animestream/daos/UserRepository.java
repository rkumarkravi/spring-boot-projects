package com.rk.animestream.daos;

import com.rk.animestream.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}