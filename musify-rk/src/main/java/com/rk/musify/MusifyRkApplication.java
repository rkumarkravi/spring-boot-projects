package com.rk.musify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class MusifyRkApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusifyRkApplication.class, args);
	}

}
