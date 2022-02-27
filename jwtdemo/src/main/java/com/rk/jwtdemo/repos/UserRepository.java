package com.rk.jwtdemo.repos;

import com.rk.jwtdemo.models.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserInfo,Long> {
    List<UserInfo> findByEmailAndPassword(String email,String password);
    List<UserInfo> findByEmail(String email);
}
