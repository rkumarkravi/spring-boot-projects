package com.rk.jwtdemo.services;

import com.rk.jwtdemo.enums.Status;
import com.rk.jwtdemo.models.UserInfo;
import com.rk.jwtdemo.repos.UserRepository;
import com.rk.jwtdemo.utility.UserUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserInfo> userInfo = userRepo.findByEmail(username);
        UserInfo user = userInfo.get(0);
        return new User(user.getEmail(),user.getPassword(),new ArrayList<>());
    }

    @Transactional
    public UserDetails authenticateUserByNameAndPassword(String username,String password) throws UsernameNotFoundException {
        List<UserInfo> userInfo = userRepo.findByEmailAndPassword(username, password);
        UserInfo user = userInfo.get(0);
        user.setActive(Status.AVAILABLE);
        userRepo.save(user);
        return new User(user.getEmail(),user.getPassword(),new ArrayList<>());
    }

    public UserInfo createUser(UserInfo userInfo){
        return userRepo.save(userInfo);
    }
    @Transactional
    public UserInfo getUserByEmailAndSetOtp(String email){
        List<UserInfo> userInfo = userRepo.findByEmail(email);
        UserInfo user=userInfo.get(0);
        if(userInfo.size()>0){
            user.setOTP(UserUtility.getOTP());
        }
        userRepo.save(user);
        return user;
    }
}
