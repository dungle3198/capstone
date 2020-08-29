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
    @GetMapping("/user_means")
    public List<UserMean> userMeans(){
        return userMeanRepository.findAll();
    }

    @CrossOrigin
    @GetMapping("/user_means/{id}")
    public ResponseEntity<UserMean> getUserMeanById(@PathVariable("id") final int id)
    {
        UserMean userMean = userMeanRepository.findById(id).get();
        return ResponseEntity.ok().body(userMean);
    }

    @CrossOrigin
    @PostMapping("/user_means")
    public void add(@RequestBody UserMean userMean)
    {
        userMeanRepository.save(userMean);
    }

    @CrossOrigin
    @DeleteMapping("/user_means/{id}")
    public void delete (@PathVariable("id") final Integer id)
    {
        userMeanRepository.deleteById(id);
    }
}
