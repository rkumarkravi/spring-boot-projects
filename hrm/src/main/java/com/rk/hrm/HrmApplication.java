package com.rk.hrm;

import com.rk.hrm.enums.GENDER;
import com.rk.hrm.enums.PRIORITY;
import com.rk.hrm.models.*;
import com.rk.hrm.repos.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
@Slf4j
public class HrmApplication implements ApplicationRunner {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private DepartmentRepository departmentRepository;
	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private TodoRepository todoRepository;
	@Autowired
	private TodoNoteRepository todoNoteRepository;

	public static void main(String[] args) {
		SpringApplication.run(HrmApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("DD/MM/YYYY");
		Department department = new Department("D1", "Dev", "open");
		departmentRepository.save(department);

		Project project=new Project("P1","String description","String status");
		projectRepository.save(project);

		User manager = new User("managerk", "String password", "Manager Kumar", "mgr@email.com", "8798564645", "String address", "Manager", "Active", "Manager", simpleDateFormat.parse("10/11/1993"), GENDER.MALE);
		manager.setProject(projectRepository.findByNameIgnoreCase("p1").get());
		manager.setDepartment(departmentRepository.findByNameIgnoreCase("D1").get());

		userRepository.save(manager);

		User dev = new User("dev1k", "String password", "Dev1 Kumar", "dev1@email.com", "8989989851", "String address", "Soft eng. 1", "Active", "String designation", simpleDateFormat.parse("10/11/1995"), GENDER.MALE);
		dev.setReportingTo(userRepository.findByEmailIgnoreCase("mgr@email.com").get());
		userRepository.save(dev);

		User dev2 = new User("dev2k", "String password", "Dev2 Kumar", "dev2@email.com", "8989989852", "String address", "Soft eng. 2", "Active", "String designation", simpleDateFormat.parse("10/11/1998"), GENDER.MALE);
		dev2.setReportingTo(userRepository.findByEmailIgnoreCase("mgr@email.com").get());
		dev2.setProject(userRepository.findByEmailIgnoreCase("mgr@email.com").get().getProject());
		userRepository.save(dev2);

		userRepository.findAll().forEach(x -> log.info("User: {}", x));
		log.info("map: {}", userRepository.findByReportingTo_Id(dev.getReportingTo().getId()));

		Todo todo = Todo.builder().date(new Date()).build();
		todo.setUser(userRepository.findByEmailIgnoreCase("mgr@email.com").get());
		todoRepository.save(todo);

		TodoNote todoNote = TodoNote.builder().priority(PRIORITY.HIGH).desc("my first Note!!").build();
		todoNote.setTodo(todo);
		todoNoteRepository.save(todoNote);
	}
}
