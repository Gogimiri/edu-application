package com.project.edu_service;

import com.project.edu_service.entityes.Users;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EduServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EduServiceApplication.class, args);
	}

	@Bean
	public Users user() {
		return new Users();
	}
}
