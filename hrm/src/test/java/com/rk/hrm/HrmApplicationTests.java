package com.rk.hrm;

import com.rk.hrm.repos.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class HrmApplicationTests {
	@Autowired
	UserRepository userRepository;
	@Test
	void contextLoads() {

//		User user = new User("String username","String password","String fullName","String email","String phone","String address","String role","String status","String designation",new Date(), GENDER.MALE);
//		Project project=new Project("String name","String description","String status");
//		user.setProject(project);
//		userRepository.save(user);
//
//		assert userRepository.findAll().size() == 1;
	}

}
