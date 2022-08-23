package com.rk.animestream.service;

import com.rk.animestream.daos.UserRepository;
import com.rk.animestream.dtos.UserDto;
import com.rk.animestream.mapper.UserMapper;
import com.rk.animestream.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    UserRepository userRepo;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    UserMapper userMapper;
    public User createUser(UserDto user) {
        String pass = passwordEncoder.encode(user.getPassword());
        user.setPassword(pass);
        return userRepo.save(userMapper.userDtoToUser(user));
    }

    @Transactional
    public UserDto authenticateUserByNameAndPassword(String username, String password) throws UsernameNotFoundException {
        log.debug("authenticateUserByNameAndPassword: {}|{}->{}",username,password,passwordEncoder.encode(password));
        Optional<User> userOptional = userRepo.findByEmailIdAndPassword(username, passwordEncoder.encode(password));

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return userMapper.userToUserDto(user);
        } else {
            log.error("authenticateUserByNameAndPassword: {}",userOptional.isPresent());
            throw new UsernameNotFoundException("User not found");
        }
    }

    public UserDto getUserByEmail(String username) throws UsernameNotFoundException {
        log.debug("getUserByEmail: {}|{}->{}",username);
        Optional<User> userOptional = userRepo.findByEmailId(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return userMapper.userToUserDto(user);
        } else {
            log.error("getUserByEmail: {}",userOptional.isPresent());
            throw new UsernameNotFoundException("User not found");
        }
    }


}
