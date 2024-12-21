package com.egjpa.rkproj.repos;

import com.egjpa.rkproj.models.RefreshToken;
import com.egjpa.rkproj.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByUser(User user);
}