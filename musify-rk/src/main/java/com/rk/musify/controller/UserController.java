package com.rk.musify.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rk.musify.model.dao.User;
import com.rk.musify.service.UserService;


@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping("/create")
	public ResponseEntity<User> insertUser(@RequestBody User user){
		return new ResponseEntity<User>(userService.createUser(user),HttpStatus.OK);
	}

}
