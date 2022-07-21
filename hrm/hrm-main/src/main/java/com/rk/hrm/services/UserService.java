package com.rk.hrm.services;

import com.rk.hrm.models.User;
import com.rk.hrm.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    public User getUserById(Long id){
        return userRepository.findById(id).get();
    }

    public Set<User> getAllResourcesAssignedToUser(Long id){
        return userRepository.findByReportingTo_Id(id);
    }

    public Set<User> getResourcesNotAllocatedToProject(){
        return userRepository.findByProjectNull();
    }
}
