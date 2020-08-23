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
    @GetMapping("/userstds")
    public List<UserStd> userStds(){
        return userStdRepository.findAll();
    }

    @CrossOrigin
    @GetMapping("/userstds/{id}")
    public ResponseEntity<UserStd> getUserStdById(@PathVariable("id") final int userId)
    {
        UserStd user = userStdRepository.findById(userId).get();
        return ResponseEntity.ok().body(user);
    }

    @CrossOrigin
    @PostMapping("/userstds")
    public void add(@RequestBody UserStd userStd)
    {
        userStdRepository.save(userStd);
    }

    @CrossOrigin
    @DeleteMapping("/userstds/{id}")
    public void delete (@PathVariable("id") final Integer id)
    {
        userStdRepository.deleteById(id);
    }

}
