package com.rk.animestream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AnimeStreamBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnimeStreamBackendApplication.class, args);
	}

}
