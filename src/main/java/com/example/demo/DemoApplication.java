package com.example.demo;

import com.example.demo.controllers.ClusterController;
import com.example.demo.controllers.UserController;
import com.example.demo.entities.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
public class DemoApplication {

	private final ClusterController clusterController;
	private final UserController userController;

	public DemoApplication(ClusterController clusterController, UserController userController) {
		this.clusterController = clusterController;
		this.userController = userController;
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	public void cluster() {
		List<User> users = userController.users();
		for (User user: users){
			List<User> listOfUsers = clusterController.createCluster(user,users);
			users.removeAll(listOfUsers);
			if (users.isEmpty()){
				break;
			}
		}
	}


}
