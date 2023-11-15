package com.rkumarkravi.instaclone.controller;

import com.rkumarkravi.instaclone.dto.ApiResponse;
import com.rkumarkravi.instaclone.dto.JwtRequest;
import com.rkumarkravi.instaclone.dto.JwtResponse;
import com.rkumarkravi.instaclone.entity.IUser;
import com.rkumarkravi.instaclone.service.UserService;
import com.rkumarkravi.instaclone.util.JWTUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<IUser>> getAllUsers() {
        List<IUser> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<IUser> getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    // Other user-related endpoints
}

