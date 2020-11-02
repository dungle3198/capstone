package com.example.demo.controllers;

import com.example.demo.entities.User;
import com.example.demo.entities.UserStats;
import com.example.demo.repositories.UserRepository;
import com.example.demo.repositories.UserStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserStatsController {

    private final UserStatsRepository userStatsRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserStatsController(UserStatsRepository userStatsRepository, UserRepository userRepository) {
        this.userStatsRepository = userStatsRepository;
        this.userRepository = userRepository;
    }

    @CrossOrigin
    @GetMapping("/user_stats/{id}")
    public UserStats getUserStatsById (@PathVariable ("id") final int id){
        return userStatsRepository.findById(id).get();
    }

    @CrossOrigin
    @GetMapping("/user_stats/user/{id}")
    public List<UserStats> getUserStatsByUserId (@PathVariable ("id") final int id){
        return userStatsRepository.getUserStatsByUserId(id);
    }

    @CrossOrigin
    @GetMapping("/user_stats")
    public List<UserStats> getAllUserStats(){
        return userStatsRepository.findAll();
    }

    @CrossOrigin
    @PostMapping("/user_stats")
    public void add(@RequestBody UserStats userStats){
        userStatsRepository.save(userStats);
    }

    @CrossOrigin
    @PutMapping("/user_stats/{id}")
    public void edit(@RequestBody UserStats userStats, @PathVariable ("id") final int id){
        User user = userRepository.findById(userStats.getUser().getId()).get();
        UserStats existingUserStats = getUserStatsById(id);
        existingUserStats.setId(id);
        existingUserStats.setUser(user);
        existingUserStats.setCategory(userStats.getCategory());
        existingUserStats.setBiller(userStats.getBiller());
        existingUserStats.setNumberOfBills(userStats.getNumberOfBills());
        existingUserStats.setMean(userStats.getMean());
        existingUserStats.setStandardDeviation(userStats.getStandardDeviation());
        userStatsRepository.save(existingUserStats);
    }

    @CrossOrigin
    @DeleteMapping("/user_stats/{id}")
    public void delete(@PathVariable ("id") final int id){
        userStatsRepository.deleteById(id);
    }
}
