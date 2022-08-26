package com.rk.animestream.service;

import com.rk.animestream.daos.UserRepository;
import com.rk.animestream.dtos.UserDto;
import com.rk.animestream.exceptions.ExpiredJwtTokenException;
import com.rk.animestream.mapper.UserMapper;
import com.rk.animestream.models.User;
import com.rk.animestream.utils.JWTUtility;
import com.rk.animestream.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.*;

@Service
@Slf4j
public class UserAuthService implements UserDetailsService {
    @Autowired
    UserRepository userRepo;
    @Autowired
    JWTUtility jwtUtility;

    @Autowired
    UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepo.findByEmailId(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
//            user.setOtp(Utils.getOTP());
//            user = userRepo.save(user);
            return new org.springframework.security.core.userdetails.User(user.getEmailId(), user.getPassword(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found");
        }

    }

    public void logoutUser(String uid) {
        Optional<User> user = userRepo.findById(Long.valueOf(uid));
        if(user.isPresent()) {
            userRepo.save(user.get());
        }else{
            throw new UsernameNotFoundException("User not found");
        }
    }

    public User checkUserActive(String token) throws ExpiredJwtTokenException {
        if(this.jwtUtility.isTokenExpired(token)){
            return null;
        }
        String email = this.jwtUtility.getUsernameFromToken(token);
        if(email==null){
            return null;
        }
        Optional<User> users = userRepo.findByEmailId(email);
        User user;
        if (users.isPresent()) {
            user = users.get();
            return user;
        } else {
            throw new UsernameNotFoundException("User not found");
        }

    }

    @Transactional
    public UserDto getUserByEmailAndSetOtp(String email) {
        Optional<User> userOptional = userRepo.findByEmailId(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setOtp(Utils.getOTP());
            return userMapper.userToUserDto(userRepo.save(user));
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    @Transactional
    public UserDto resetUserPassword(Map<String, Object> value) {
        Optional<User> userOptional = userRepo.findById(Long.valueOf((Integer) value.get("uidFrRstPsd")));

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setPassword((String) value.get("newPassword"));

            return userMapper.userToUserDto(userRepo.save(user));
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    public Map<String, Object> validateOtp(Map<String, String> val) {
        boolean otpCorrect;
        log.debug("validateOtp:{}", val);
        Optional<User> userOptional = userRepo.findByEmailId(val.get("email"));

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            otpCorrect = user.getOtp().equals(val.get("otp"));

            Map<String, Object> jsonObject = new HashMap<>();
            jsonObject.put("msg", otpCorrect ? "Loading re-set password page!" : "Invalid OTP!");
            jsonObject.put("OTPStatus", otpCorrect);
            jsonObject.put("userid", user.getUId());

            return jsonObject;
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    public UserDto getUserFromToken(HttpServletRequest httpServletRequest) throws ExpiredJwtTokenException {
        String username = jwtUtility.getUsernameFromToken(httpServletRequest.getHeader("Authorization").split(" ")[1]);
        return this.getUserByEmailAndSetOtp(username);

    }


}
