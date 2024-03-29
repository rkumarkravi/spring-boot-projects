package com.rk.jwtdemo.services;

import com.rk.jwtdemo.enums.Status;
import com.rk.jwtdemo.models.UserInfo;
import com.rk.jwtdemo.repos.UserRepository;
import com.rk.jwtdemo.utility.JWTUtility;
import com.rk.jwtdemo.utility.UserUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.*;

@Service
@Slf4j
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepo;
    @Autowired
    private JWTUtility jwtUtility;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserInfo> userInfo = userRepo.findByEmail(username);
        UserInfo user = userInfo.get(0);
        return new User(user.getEmail(),user.getPassword(),new ArrayList<>());
    }

    @Transactional
    public UserInfo authenticateUserByNameAndPassword(String username,String password) throws UsernameNotFoundException {
        List<UserInfo> userInfo = userRepo.findByEmailAndPassword(username, password);
        UserInfo user = userInfo.get(0);
        user.setActive(Status.AVAILABLE);
        return userRepo.save(user);
    }

    public UserInfo createUser(UserInfo userInfo){
        return userRepo.save(userInfo);
    }
    @Transactional
    public UserInfo getUserByEmailAndSetOtp(String email){
        List<UserInfo> userInfo = userRepo.findByEmail(email);
        UserInfo user=userInfo.get(0);
        if(userInfo.size()>0){
            user.setOtp(UserUtility.getOTP());
        }
        userRepo.save(user);
        return user;
    }

    @Transactional
    public UserInfo resetUserPassword(Map<String,Object> value){
        Optional<UserInfo> userInfo = userRepo.findById(Long.valueOf((Integer) value.get("uidFrRstPsd")));
        UserInfo user = userInfo.get();
        if(user!=null){
            user.setPassword((String) value.get("newPassword"));
        }
        userRepo.save(user);
        return user;
    }

    public Map<String,Object> validateOtp(Map<String,String> val) {
        Boolean otpCorrect=false;
        log.debug("validateOtp:{}",val);
        List<UserInfo> userInfo = userRepo.findByEmail(val.get("email"));
        UserInfo user=userInfo.get(0);
        if(userInfo.size()>0){
            otpCorrect=user.getOtp().equals(val.get("otp"));
        }
        Map<String,Object> jsonpObject=new HashMap<>();
        jsonpObject.put("msg",otpCorrect?"Loading re-set password page!":"Invalid OTP!");
        jsonpObject.put("OTPStatus",otpCorrect);
        jsonpObject.put("userid",user.getUserid());
        return jsonpObject;
    }

    public void logoutUser(String uid) {
        Optional<UserInfo> userInfo = userRepo.findById(Long.valueOf(uid));
        UserInfo user = userInfo.get();
        if(user!=null){
            user.setActive(Status.OFFLINE);
        }
        userRepo.save(user);
    }

    public UserInfo checkUserActive(String token){
        String email=this.jwtUtility.getUsernameFromToken(token);
        List<UserInfo> users = userRepo.findByEmail(email);
        UserInfo user=null;
        if(users.size()>0) {
            user = users.get(0);
        }
        return user;

    }
    

    public UserInfo getUserFromToken(HttpServletRequest httpServletRequest){
    	String username = jwtUtility.getUsernameFromToken(httpServletRequest.getHeader("Authorization").split(" ")[1]);
		UserInfo uinfo = this.getUserByEmailAndSetOtp(username);
		
		return uinfo;
		
    }
}
