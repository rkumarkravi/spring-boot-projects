package com.rk.musify.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rk.musify.model.dao.User;
import com.rk.musify.repository.UserDao;

@Service
public class UserService {
	@Autowired
	UserDao userDao;

	public User createUser(User user) {

		return userDao.save(user);
	}
}
