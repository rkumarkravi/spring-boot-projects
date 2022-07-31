package com.rk.musify.controller;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.WriterException;
import com.rk.musify.model.dao.User;
import com.rk.musify.service.UserService;
import com.rk.musify.util.Util;


@RestController
@RequestMapping("/user")
//@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping("/create")
	public ResponseEntity<User> insertUser(@RequestBody User user){
		return new ResponseEntity<User>(userService.createUser(user),HttpStatus.OK);
	}
	
	@PostMapping(path="/login")
	public ResponseEntity<Map<String,Object>> loginUser(@RequestBody Map<String,String> uData){
		return new ResponseEntity<Map<String,Object>>(userService.validUser(uData),HttpStatus.OK);
	}
	
	@GetMapping("/createQR")
	public ResponseEntity<String> generateQr(@RequestParam("text") String qrtext) throws WriterException, IOException{
		String qrcode = Base64.getEncoder().encodeToString(Util.getQRCodeImage(qrtext, 100, 100));
		return new ResponseEntity(qrcode,HttpStatus.OK);
	}

}
