package com.example.demo.controllers;

import com.example.demo.entities.InternetMeanMonth;
import com.example.demo.repositories.InternetMeanMonthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class InternetMeanMonthController {

    private final InternetMeanMonthRepository internetMeanMonthRepository;

    @Autowired
    public InternetMeanMonthController(InternetMeanMonthRepository internetMeanMonthRepository) {
        this.internetMeanMonthRepository = internetMeanMonthRepository;
    }

    @CrossOrigin
    @GetMapping("/internet_mean_month")
    public List<InternetMeanMonth> internetMeanMonthList(){
        return internetMeanMonthRepository.findAll();
    }
}
