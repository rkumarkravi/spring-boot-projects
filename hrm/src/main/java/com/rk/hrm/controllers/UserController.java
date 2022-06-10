package com.rk.hrm.controllers;

import com.rk.hrm.dtos.UserDto;
import com.rk.hrm.mappers.UserMapper;
import com.rk.hrm.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/get/{id}")
    public UserDto getUsers(@PathVariable("id") Long id) {
        return userMapper.userToUserDto(userService.getUserById(id));
    }

    @GetMapping("/assignedToManager/{id}")
    public Set<UserDto> getAllUsersAssignedToManager(@PathVariable("id") Long id){
       return userService.getAllResourcesAssignedToUser(id).parallelStream().map(res->userMapper.userToUserDto(res)).collect(Collectors.toSet());
    }

//    @PatchMapping("/reassignToAnotherManager/{id}")
//    public UserDto registerUser(){
//
//    }

        @GetMapping("/getResourcesNotAllocatedToProject")
    public Set<UserDto> getResourcesNotAllocatedToProject(){
        return this.userService.getResourcesNotAllocatedToProject().parallelStream().map(res->userMapper.userToUserDto(res)).collect(Collectors.toSet());
    }

}
