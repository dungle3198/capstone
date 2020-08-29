package com.example.demo.controllers;

import com.example.demo.entities.WaterMeanMonth;
import com.example.demo.repositories.WaterMeanMonthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WaterMeanMonthController {

    private final WaterMeanMonthRepository waterMeanMonthRepository;

    @Autowired
    public WaterMeanMonthController(WaterMeanMonthRepository waterMeanMonthRepository) {
        this.waterMeanMonthRepository = waterMeanMonthRepository;
    }

    @CrossOrigin
    @GetMapping("/water_mean_month")
    public List<WaterMeanMonth> waterMeanMonthList(){
        return waterMeanMonthRepository.findAll();
    }
}
