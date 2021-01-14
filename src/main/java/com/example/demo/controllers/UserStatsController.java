package com.example.demo.controllers;

import com.example.demo.entities.User;
import com.example.demo.entities.UserStats;
import com.example.demo.repositories.UserStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserStatsController {

    private final UserStatsRepository userStatsRepository;
    private final UserController userController;

    @Autowired
    public UserStatsController(UserStatsRepository userStatsRepository, UserController userController) {
        this.userStatsRepository = userStatsRepository;
        this.userController = userController;
    }

    @CrossOrigin
    @GetMapping("/user_stats/{id}")
    public Optional<UserStats> getUserStatsById (@PathVariable ("id") final int id){
        return userStatsRepository.findById(id);
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
        UserStats existingUserStats;
        User user;
        if (userController.getUserById(userStats.getUser().getId()).isPresent() && getUserStatsById(id).isPresent()){
            existingUserStats = getUserStatsById(id).get();
            user = userController.getUserById(userStats.getUser().getId()).get();
        }
        else {return;}
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
