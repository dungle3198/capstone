package com.example.demo.controllers;

import java.util.List;


import com.example.demo.repositories.UserStdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entities.UserStd;
import com.example.demo.repositories.UserStdRepository;

@RestController
public class UserStdController {

    private final UserStdRepository userStdRepository;

    @Autowired
    public UserStdController(UserStdRepository userStdRepository) {
        this.userStdRepository = userStdRepository;
    }

    @CrossOrigin
    @GetMapping("/user_stds")
    public List<UserStd> userStds(){
        return userStdRepository.findAll();
    }

    @CrossOrigin
    @GetMapping("/user_stds/{id}")
    public ResponseEntity<UserStd> getUserStdById(@PathVariable("id") final int user_id)
    {
        UserStd userStd = userStdRepository.findById(user_id).get();
        return ResponseEntity.ok().body(userStd);
    }

    @CrossOrigin
    @PostMapping("/user_stds")
    public void add(@RequestBody UserStd userStd)
    {
        userStdRepository.save(userStd);
    }

    @CrossOrigin
    @DeleteMapping("/user_stds/{id}")
    public void delete (@PathVariable("id") final Integer id)
    {
        userStdRepository.deleteById(id);
    }
}
