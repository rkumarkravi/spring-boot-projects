package com.rkumarkravi.instaclone.dao.sql;

import com.rkumarkravi.instaclone.entity.IJwtToken;
import com.rkumarkravi.instaclone.entity.IUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface IJwtTokenRepository extends JpaRepository<IJwtToken, Long> {
    long deleteByUser(@NonNull IUser user);

    @Query("select i from IJwtToken i where i.deviceMode = ?1 and i.authToken = ?2 and i.user.username = ?3")
    Optional<IJwtToken> findByDeviceModeAndAuthTokenAndUser_Username(String deviceMode, String authToken, String username);



}