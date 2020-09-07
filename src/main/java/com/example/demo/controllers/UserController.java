package com.example.demo.controllers;

import java.util.List;


import com.example.demo.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<User> getUserById(@PathVariable("id") final int user_id)
	{
		User user = userRepository.findById(user_id).get();
		return ResponseEntity.ok().body(user);
	}


	public void setUserRelation(User user, UserMean userMean, UserStd userStd, GasMeanMonth gasMeanMonth,
								InternetMeanMonth internetMeanMonth, ElectricityMeanMonth electricityMeanMonth,
								WaterMeanMonth waterMeanMonth){
		user.setUserMean(userMean);
		user.setUserStd(userStd);
		user.setGasMeanMonth(gasMeanMonth);
		user.setInternetMeanMonth(internetMeanMonth);
		user.setElectricityMeanMonth(electricityMeanMonth);
		user.setWaterMeanMonth(waterMeanMonth);
		userMean.setUser(user);
		userStd.setUser(user);
		gasMeanMonth.setUser(user);
		internetMeanMonth.setUser(user);
		electricityMeanMonth.setUser(user);
		waterMeanMonth.setUser(user);
	}

	@CrossOrigin
	@PostMapping("/users")
	public void add(@RequestBody User user)
	{
		UserMean userMean = new UserMean();
		UserStd userStd = new UserStd();
		GasMeanMonth gasMeanMonth = new GasMeanMonth();
		InternetMeanMonth internetMeanMonth = new InternetMeanMonth();
		ElectricityMeanMonth electricityMeanMonth = new ElectricityMeanMonth();
		WaterMeanMonth waterMeanMonth = new WaterMeanMonth();
		setUserRelation(user, userMean, userStd, gasMeanMonth, internetMeanMonth, electricityMeanMonth, waterMeanMonth);
		userRepository.save(user);
	}

	@CrossOrigin
	@PutMapping("/users/{id}")
	public void edit(@RequestBody User user, @PathVariable("id") final Integer id)
	{
		User existedUser = userRepository.findById(id).get();
		UserMean userMean = existedUser.getUserMean();
		UserStd userStd = existedUser.getUserStd();
		GasMeanMonth gasMeanMonth = existedUser.getGasMeanMonth();
		InternetMeanMonth internetMeanMonth = existedUser.getInternetMeanMonth();
		ElectricityMeanMonth electricityMeanMonth = existedUser.getElectricityMeanMonth();
		WaterMeanMonth waterMeanMonth = existedUser.getWaterMeanMonth();
		existedUser.setId(user.getId());
		existedUser.setFirst_name(user.getFirst_name());
		existedUser.setLast_name(user.getLast_name());
		setUserRelation(existedUser, userMean, userStd, gasMeanMonth, internetMeanMonth, electricityMeanMonth, waterMeanMonth);
		userRepository.save(existedUser);
	}

	@CrossOrigin
	@DeleteMapping("/users/{id}")
	public void delete (@PathVariable("id") final Integer id)
	{
		userRepository.deleteById(id);
	}
}
