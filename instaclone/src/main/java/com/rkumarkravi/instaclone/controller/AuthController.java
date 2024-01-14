package com.rkumarkravi.instaclone.controller;

import com.rkumarkravi.instaclone.dao.sql.IJwtTokenRepository;
import com.rkumarkravi.instaclone.dto.ApiResponse;
import com.rkumarkravi.instaclone.dto.JwtRequest;
import com.rkumarkravi.instaclone.dto.JwtResponse;
import com.rkumarkravi.instaclone.entity.IJwtToken;
import com.rkumarkravi.instaclone.entity.IUser;
import com.rkumarkravi.instaclone.service.UserService;
import com.rkumarkravi.instaclone.util.JWTUtility;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JWTUtility jwtUtility;

    private final IJwtTokenRepository iJwtTokenRepository;

    @Autowired
    public AuthController(UserService userService,
                          AuthenticationManager authenticationManager,
                          JWTUtility jwtUtility,
                          IJwtTokenRepository iJwtTokenRepository) {
        this.userService = userService;
        this.authenticationManager=authenticationManager;
        this.jwtUtility=jwtUtility;
        this.iJwtTokenRepository=iJwtTokenRepository;
    }
    @PostMapping("/signup")
    public ResponseEntity<IUser> createUser(@RequestBody IUser user) {
        Set<String> set=new HashSet<>();
        set.add("ROLE_USER");
        IUser createdUser = userService.createUser(user,set);
        return ResponseEntity.ok(createdUser);
    }

    @PostMapping("/signin")
    @Transactional
    public ApiResponse<JwtResponse> authUser(@RequestBody JwtRequest jwtRequest, HttpServletRequest httpServletRequest) throws Exception {
        log.debug("authenticate: {}", jwtRequest);
        ApiResponse<JwtResponse> apiResponse = new ApiResponse<>();
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
        IUser user = (IUser) userService.loadUserByUsername(jwtRequest.getUsername());

        //invalidate the old token if already there for same user
        iJwtTokenRepository.deleteByUser(user);

        //add claims
        Map<String, Object> otherDetails = new HashMap<>();
        otherDetails.put("uid", user.getId());

        //get tokens for auth and refresh
        Map<String,String> tokens = jwtUtility.generateToken(user.getUsername(), otherDetails);

        //add more data for user in response
        otherDetails.put("username",user.getUsername());
        otherDetails.putAll(tokens);

        IJwtToken iJwtToken=new IJwtToken();
        iJwtToken.setUser(user);
        iJwtToken.setAuthToken(tokens.get("authToken"));
        iJwtToken.setRefreshToken(tokens.get("refreshToken"));
        if(null!=httpServletRequest.getHeader("mode")) {
            iJwtToken.setDeviceMode(httpServletRequest.getHeader("mode"));
        }else{
            iJwtToken.setDeviceMode("ANONYMOUS");
        }
        iJwtTokenRepository.save(iJwtToken);

        apiResponse.setStatus("S");
        apiResponse.setData(new JwtResponse(otherDetails));
        return apiResponse;
    }
}
