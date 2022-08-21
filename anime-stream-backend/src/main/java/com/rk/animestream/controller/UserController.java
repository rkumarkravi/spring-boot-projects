package com.rk.animestream.controller;

import com.rk.animestream.dtos.JwtRequest;
import com.rk.animestream.dtos.JwtResponse;
import com.rk.animestream.dtos.UserDto;
import com.rk.animestream.service.UserAuthService;
import com.rk.animestream.service.UserService;
import com.rk.animestream.utils.JWTUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@Slf4j
public class UserController {

    @Autowired
    private JWTUtility jwtUtility;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home() {
        log.debug("abcfjdshksjd");
        return "Welcome to anime streamer!!";
    }

    @GetMapping("/userinfo")
    public ResponseEntity<UserDto> userInfo(HttpServletRequest httpServletRequest) {
        return new ResponseEntity<UserDto>(userAuthService.getUserFromToken(httpServletRequest), HttpStatus.OK);
    }

    @PostMapping("/login")
    public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest) throws Exception {
        log.debug("authenticate: {}",jwtRequest);
        try {authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(jwtRequest.getEmailId(), jwtRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
        UserDto user = userService.getUserByEmail(jwtRequest.getEmailId());
        final UserDetails userDetails = new User(user.getEmailId(), user.getPassword(), new ArrayList<>());

        final String token = jwtUtility.generateToken(userDetails);

        return new JwtResponse(token, user);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto user) throws Exception {
        Map<String, String> jsonObject = new HashMap<>();
        jsonObject.put("username", userService.createUser(user).getEmailId());
        jsonObject.put("status", "SUCCESS");
        return ResponseEntity.ok(jsonObject);
    }

    @PostMapping("/password/reset")
    public ResponseEntity<?> resetPasswordUserValidation(@RequestBody String email) throws Exception {
        Map<String, String> jsonObject = new HashMap<>();
        UserDto userInfo = userAuthService.getUserByEmailAndSetOtp(email);
        jsonObject.put("msg", "OTP sent on your registered mail id !");
        jsonObject.put("OTP", userInfo.getOtp());
        return ResponseEntity.ok(jsonObject);
    }

    @PostMapping("/otp/validate")
    public ResponseEntity<?> otpValidate(@RequestBody Map<String, String> otpData) throws Exception {
        Map<String, Object> validationMap = userAuthService.validateOtp(otpData);
        return ResponseEntity.ok(validationMap);
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, Object> mapValue) throws Exception {
        Map<String, String> jsonObject = new HashMap<>();
        UserDto userInfo = userAuthService.resetUserPassword(mapValue);
        jsonObject.put("msg", "Password reset successful!");
        jsonObject.put("userEmail", userInfo.getEmailId());
        return ResponseEntity.ok(jsonObject);
    }

    @GetMapping("/logout/{uid}")
    public ResponseEntity<?> logout(@PathVariable("uid") String uid) throws Exception {
        Map<String, String> jsonObject = new HashMap<>();
        userAuthService.logoutUser(uid);
        jsonObject.put("msg", "Logged out successfully!");
        return ResponseEntity.ok(jsonObject);
    }

    @PostMapping("/validate")
    public ResponseEntity<?> tokenExpired(@RequestParam("t") String token) {
        log.info(token);
        if (token == "") {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(userAuthService.checkUserActive(token));
    }
}
