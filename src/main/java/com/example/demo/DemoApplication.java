package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DemoApplication {

	public static void main(String[] args) {
//		System.out.println("Adding Bill");
//		UserRepositoryV2 repo = new UserRepositoryV2();
//		repo.addUser(new UserV2("1","Dung","Le",0,0));
		SpringApplication.run(DemoApplication.class, args);
	}
}
