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

    @GetMapping("/userstds")
    public List<UserStd> userStds(){
        return userStdRepository.findAll();
    }
    @GetMapping("/userstds/{id}")
    public ResponseEntity<UserStd> getUserStdById(@PathVariable(value = "1") int userId)
    {
        UserStd user = userStdRepository.findById(userId).get();
        return ResponseEntity.ok().body(user);
    }
    @PostMapping("/userstds")
    public void add(@RequestBody UserStd userStd)
    {
        userStdRepository.save(userStd);
    }
    @PutMapping("/userstds/{id}")
    public void edit(@RequestBody UserStd userStd, @PathVariable Integer id)
    {
        UserStd existedUser = userStdRepository.findById(id).get();
        userStdRepository.save(existedUser);
    }
    @DeleteMapping("/userstds/{id}")
    public void delete (@PathVariable Integer id)
    {
        userStdRepository.deleteById(id);
    }

}
