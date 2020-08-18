package com.example.demo.controllers;

import java.util.List;


import com.example.demo.repositories.UserMeanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entities.UserMean;
import com.example.demo.repositories.UserMeanRepository;

@RestController
public class UserMeanController {

    private final UserMeanRepository userMeanRepository;

    @Autowired
    public UserMeanController(UserMeanRepository userMeanRepository) {
        this.userMeanRepository = userMeanRepository;
    }

    @CrossOrigin
    @GetMapping("/usermeans")
    public List<UserMean> userMeans(){
        return userMeanRepository.findAll();
    }

    @CrossOrigin
    @GetMapping("/usermeans/{id}")
    public ResponseEntity<UserMean> getUserMeanById(@PathVariable("id") final int userId)
    {
        UserMean user = userMeanRepository.findById(userId).get();
        return ResponseEntity.ok().body(user);
    }

    @CrossOrigin
    @PostMapping("/usermeans")
    public void add(@RequestBody UserMean userMean)
    {
        userMeanRepository.save(userMean);
    }

    @CrossOrigin
    @PutMapping("/usermeans/{id}")
    public void edit(@RequestBody UserMean userMean, @PathVariable("id") final Integer id)
    {
        UserMean existedUser = userMeanRepository.findById(id).get();
        existedUser.setId(userMean.getId());
        existedUser.setElectricity(userMean.getElectricity());
        existedUser.setGas(userMean.getGas());
        existedUser.setInternet(userMean.getInternet());
        existedUser.setWater(userMean.getWater());
        userMeanRepository.save(existedUser);
    }

    @CrossOrigin
    @DeleteMapping("/usermeans/{id}")
    public void delete (@PathVariable("id") final Integer id)
    {
        userMeanRepository.deleteById(id);
    }

}
