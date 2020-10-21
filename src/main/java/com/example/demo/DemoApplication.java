package com.example.demo;

//import com.example.demo.entities.V2.BillV2;
//import com.example.demo.entities.V2.UserV2;
//import com.example.demo.repositories.V2.BillRepositoryV2;
//import com.example.demo.repositories.V2.UserRepositoryV2;
import org.joda.time.DateTime;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@SpringBootApplication
@RestController
public class DemoApplication {

	public static void main(String[] args) {
		//Add User
		//System.out.println("Adding Bill");
		//UserRepositoryV2 repo = new UserRepositoryV2();
		//repo.addUser(new UserV2("2","Dung","Le",0,0));

		//Add Bill
//		BillRepositoryV2 repo2 = new BillRepositoryV2();
//		repo2.addBillV2(new BillV2("1",
//				"1",
//				LocalDate.now(),
//				900,
//				"water",
//				"4",
//				"Ho Chi Minh",10,0));
		SpringApplication.run(DemoApplication.class, args);
	}
}
