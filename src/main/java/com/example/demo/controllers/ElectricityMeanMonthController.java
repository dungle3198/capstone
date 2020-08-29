package com.example.demo.controllers;

import com.example.demo.entities.ElectricityMeanMonth;
import com.example.demo.repositories.ElectricityMeanMonthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ElectricityMeanMonthController {

    private final ElectricityMeanMonthRepository electricityMeanMonthRepository;

    @Autowired
    public ElectricityMeanMonthController(ElectricityMeanMonthRepository electricityMeanMonthRepository) {
        this.electricityMeanMonthRepository = electricityMeanMonthRepository;
    }

    @CrossOrigin
    @GetMapping("/electricity_mean_month")
    public List<ElectricityMeanMonth> electricityMeanMonthList(){
        return electricityMeanMonthRepository.findAll();
    }
}
