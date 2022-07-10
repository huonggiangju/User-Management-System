package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class CrudProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudProjectApplication.class, args);
		

//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		String raw = "12345";
//
//	
//		String encodePassword = encoder.encode(raw);
//		System.out.println(encodePassword);
	}

}
