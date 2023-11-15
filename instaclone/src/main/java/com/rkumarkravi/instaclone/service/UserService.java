package com.rkumarkravi.instaclone.service;

import com.rkumarkravi.instaclone.entity.IRole;
import com.rkumarkravi.instaclone.entity.IUser;
import com.rkumarkravi.instaclone.dao.sql.RoleRepository;
import com.rkumarkravi.instaclone.dao.sql.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<IUser> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<IUser> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public Optional<IUser> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public IUser createUser(IUser user, Set<String> roleNames) {
        // Encode the user's password before saving to the database
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Set roles for the user
        Set<IRole> roles = roleRepository.findByNameIn(roleNames);
        user.setRoles(roles);

        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<IUser> user = userRepository.findByUsername(username);
        if(user.isPresent()){
            log.info("user found {}",user.get());
            return user.get();
        }else {
            log.info("user not found {}",user);
            throw new UsernameNotFoundException("no user found !");
        }
    }

    // Other user-related methods
}

