package com.example.demo.controllers;

import java.util.List;
import java.util.Optional;


import com.example.demo.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
	public Optional<User> getUserById(@PathVariable("id") final int id)
	{
		return userRepository.findById(id);
	}

	public List<User> getUsersWithBills(){
		return userRepository.getUsersWithBills();
	}

	@CrossOrigin
	@PostMapping("/users")
	public void add(@RequestBody User user)
	{
		userRepository.save(user);
	}

	@CrossOrigin
	@PutMapping("/users/{id}")
	public void edit(@RequestBody User user, @PathVariable("id") final int id) {
		User existedUser;
		if (getUserById(id).isPresent()){
			existedUser = getUserById(id).get();
		}
		else {return;}
		existedUser.setId(id);
		existedUser.setFirstName(user.getFirstName());
		existedUser.setLastName(user.getLastName());
		userRepository.save(existedUser);
	}

	@CrossOrigin
	@DeleteMapping("/users/{id}")
	public void delete(@PathVariable("id") final int id)
	{
		userRepository.deleteById(id);
	}

	@CrossOrigin
	@GetMapping("/users/delete")
	public void deleteBillNumber(){
		List<User> userList = users();
		for (User user : userList){
			user.setTotalBill(0);
			userRepository.save(user);
		}
	}
}
