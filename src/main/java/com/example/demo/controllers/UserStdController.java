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
    @PutMapping("/userstds/{id}")
    public void edit(@RequestBody UserStd userStd, @PathVariable("id") final Integer id)
    {
        UserStd existedUser = userStdRepository.findById(id).get();
        existedUser.setId(userStd.getId());
        existedUser.setElectricity(userStd.getElectricity());
        existedUser.setGas(userStd.getGas());
        existedUser.setInternet(userStd.getInternet());
        existedUser.setWater(userStd.getWater());
        userStdRepository.save(existedUser);
    }

    @CrossOrigin
    @DeleteMapping("/userstds/{id}")
    public void delete (@PathVariable("id") final Integer id)
    {
        userStdRepository.deleteById(id);
    }

}
