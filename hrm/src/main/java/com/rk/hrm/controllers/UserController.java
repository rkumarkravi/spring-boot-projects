package com.rk.hrm.controllers;

import com.rk.hrm.dtos.UserDto;
import com.rk.hrm.mappers.UserMapper;
import com.rk.hrm.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;
    @Autowired
    private MessageSource messageSource;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/greet")
    public String greetings(
//            @RequestHeader(name = "Accept-Language",required = false) Locale locale
    ) {
        log.info("Local is {}", LocaleContextHolder.getLocale());
        return messageSource.getMessage("user.greeting", null, "Good Morning", LocaleContextHolder.getLocale());
    }

    @GetMapping("/{id}")
    public EntityModel<UserDto> getUsers(@PathVariable("id") Long id) {
        UserDto userData = userMapper.userToUserDto(userService.getUserById(id));
        EntityModel<UserDto> entityModel = EntityModel.of(userData);
        WebMvcLinkBuilder webMvcLinkBuilder = null;
        if (userData.getReportingTo() != null) {
            webMvcLinkBuilder = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getUsers(userData.getReportingTo().getId()));
            entityModel.add(webMvcLinkBuilder.withRel("manager"));
        }
        return entityModel;
    }

    @GetMapping("/assigned-to-manager/{id}")
    public Set<UserDto> getAllUsersAssignedToManager(@PathVariable("id") Long id) {
        return userService.getAllResourcesAssignedToUser(id).parallelStream().map(userMapper::userToUserDto).collect(Collectors.toSet());
    }

//    @PatchMapping("/reassignToAnotherManager/{id}")
//    public UserDto registerUser(){
//
//    }

    @GetMapping("/get-resources-not-allocated-to-project")
    public Set<UserDto> getResourcesNotAllocatedToProject(){
        return this.userService.getResourcesNotAllocatedToProject().parallelStream().map(userMapper::userToUserDto).collect(Collectors.toSet());
    }

}
