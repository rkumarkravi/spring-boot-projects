package com.rk.jwtdemo.controllers;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.rk.jwtdemo.models.JwtRequest;
import com.rk.jwtdemo.models.JwtResponse;
import com.rk.jwtdemo.models.UserInfo;
import com.rk.jwtdemo.services.UserService;
import com.rk.jwtdemo.utility.JWTUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@CrossOrigin(originPatterns = "*")
public class SecurityController {
    @Autowired
    private JWTUtility jwtUtility;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home() {
        log.debug("abcfjdshksjd");
        return "Welcome to Spring security!!";
    }

    @PostMapping("/authenticate/login")
    public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest) throws Exception{

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequest.getUsername(),
                            jwtRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        UserInfo user=userService.authenticateUserByNameAndPassword(jwtRequest.getUsername(),jwtRequest.getPassword());
        final UserDetails userDetails
                = new User(user.getEmail(),user.getPassword(),new ArrayList<>());

        final String token =
                jwtUtility.generateToken(userDetails);

        return  new JwtResponse(token,user);
    }
    @PostMapping("/authenticate/register")
    public ResponseEntity register(@RequestBody UserInfo user) throws Exception{
        Map<String,String> jsonpObject=new HashMap<>();
        jsonpObject.put("username",userService.createUser(user).getFirstName());
        jsonpObject.put("status","SUCCESS");
        return ResponseEntity.ok(jsonpObject);
    }
    @PostMapping("/authenticate/resetPwdUserValidate")
    public ResponseEntity resetPasswordUserValidation(@RequestBody String email) throws Exception{
        Map<String,String> jsonpObject=new HashMap<>();
        UserInfo userInfo = userService.getUserByEmailAndSetOtp(email);
        jsonpObject.put("msg","OTP sent on your registered mail id !");
        jsonpObject.put("OTP",userInfo.getOTP());
        return ResponseEntity.ok(jsonpObject);
    }
    @PostMapping("/authenticate/otpvalidate")
    public ResponseEntity otpValidate(@RequestBody Map<String,String> otpData) throws Exception{
        Map<String,Object> validationMap = userService.validateOtp(otpData);
        return ResponseEntity.ok(validationMap);
    }
    @PostMapping("/authenticate/resetPassword")
    public ResponseEntity resetPassword(@RequestBody Map<String,Object> mapValue) throws Exception{
        Map<String,String> jsonpObject=new HashMap<>();
        UserInfo userInfo = userService.resetUserPassword(mapValue);
        jsonpObject.put("msg","Password reset successful!");
        jsonpObject.put("userEmail",userInfo.getEmail());
        return ResponseEntity.ok(jsonpObject);
    }

    @GetMapping("/authenticate/logout/{uid}")
    public ResponseEntity resetPassword(@PathVariable("uid") String uid) throws Exception{
        Map<String,String> jsonpObject=new HashMap<>();
        userService.logoutUser(uid);
        jsonpObject.put("msg","Logged out successfully!");
        return ResponseEntity.ok(jsonpObject);
    }
}
