package com.rk.animestream.daos;

import com.rk.animestream.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where u.emailId = ?1")
    Optional<User> findByEmailId(@NonNull String emailId);
    @Query("select u from User u where u.emailId = ?1 and u.password = ?2")
    Optional<User> findByEmailIdAndPassword(@NonNull String emailId, @NonNull String password);

}