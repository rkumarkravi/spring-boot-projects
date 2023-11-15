package com.rkumarkravi.instaclone.dao.sql;

import com.rkumarkravi.instaclone.entity.IUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<IUser, Long> {

    Optional<IUser> findByUsername(String username);

    // Other custom queries if needed
}

