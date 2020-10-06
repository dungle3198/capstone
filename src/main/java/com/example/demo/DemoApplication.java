package com.example.demo;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.example.demo.config.DynamoDBConfig;
import com.example.demo.entities.UserV2;
import com.example.demo.repositories.UserRepositoryV2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

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
