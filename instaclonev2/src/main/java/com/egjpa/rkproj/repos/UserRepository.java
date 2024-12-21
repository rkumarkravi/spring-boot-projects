package com.egjpa.rkproj.repos;

import com.egjpa.rkproj.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where u.username = ?1 or u.email = ?1")
    Optional<User> findByUsernameOrEmail(String info);
}