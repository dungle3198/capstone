package com.example.demo.controllers;

import java.util.List;


import com.example.demo.entities.UserMean;
import com.example.demo.entities.UserStd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entities.User;
import com.example.demo.repositories.UserRepository;

@RestController
public class UserController {

	private final UserRepository userRepository;
	
	@Autowired
	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@CrossOrigin
	@GetMapping("/users")
	public List<User> users(){
		return userRepository.findAll();
	}

	@CrossOrigin
	@GetMapping("/users/{id}")
	public ResponseEntity<User> getUserById(@PathVariable("id") final int userId)
	{
		User user = userRepository.findById(userId).get();
		return ResponseEntity.ok().body(user);
	}

	@CrossOrigin
	@PostMapping("/users")
	public void add(@RequestBody User user)
	{
		UserMean userMean = new UserMean();
		UserStd userStd = new UserStd();
		user.setUserMean(userMean);
		user.setUserStd(userStd);
		userMean.setUser(user);
		userStd.setUser(user);
		userRepository.save(user);
	}

	@CrossOrigin
	@PutMapping("/users/{id}")
	public void edit(@RequestBody User user, @PathVariable("id") final Integer id)
	{
		User existedUser = userRepository.findById(id).get();
		existedUser.setId(user.getId());
		existedUser.setFirst_name(user.getFirst_name());
		existedUser.setLast_name(user.getLast_name());
		userRepository.save(existedUser);
	}

	@CrossOrigin
	@DeleteMapping("/users/{id}")
	public void delete (@PathVariable("id") final Integer id)
	{
		userRepository.deleteById(id);
	}

}
