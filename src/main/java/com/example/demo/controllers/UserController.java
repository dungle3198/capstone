package com.example.demo.controllers;

import java.util.List;


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
	
	@GetMapping("/users")
	public List<User> users(){
		return userRepository.findAll();
	}
	@GetMapping("/users/{id}")
	public ResponseEntity<User> getUserById(@PathVariable(value = "1") int userId)
	{
		User user = userRepository.findById(userId).get();
		return ResponseEntity.ok().body(user);
	}
	@PostMapping("/users")
	public void add(@RequestBody User user)
	{
		userRepository.save(user);
	}
	@PutMapping("/users/{id}")
	public void add(@RequestBody User user, @PathVariable Integer id)
	{
		User existedUser = userRepository.findById(id).get();
		userRepository.save(existedUser);
	}
	@DeleteMapping("/users/{id}")
	public void delete (@PathVariable Integer id)
	{
		userRepository.deleteById(id);
	}

}
