package com.example.demo.controllers;

import com.example.demo.entities.InternetMeanMonth;
import com.example.demo.repositories.InternetMeanMonthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @CrossOrigin
    @PutMapping("/internet_mean_month/{id}")
    public void editInternetMeanMonth(@RequestBody InternetMeanMonth internetMeanMonth, @PathVariable("id") Integer id)
    {
        InternetMeanMonth existedInternetMeanMonth = internetMeanMonthRepository.findById(id).get();
        existedInternetMeanMonth.setId(internetMeanMonth.getId());
        existedInternetMeanMonth.setInternetMeanMonth1(internetMeanMonth.getInternetMeanMonth1());
        existedInternetMeanMonth.setInternetMeanMonth2(internetMeanMonth.getInternetMeanMonth2());
        existedInternetMeanMonth.setInternetMeanMonth3(internetMeanMonth.getInternetMeanMonth3());
        existedInternetMeanMonth.setInternetMeanMonth4(internetMeanMonth.getInternetMeanMonth4());
        existedInternetMeanMonth.setInternetMeanMonth5(internetMeanMonth.getInternetMeanMonth5());
        existedInternetMeanMonth.setInternetMeanMonth6(internetMeanMonth.getInternetMeanMonth6());
        existedInternetMeanMonth.setInternetMeanMonth7(internetMeanMonth.getInternetMeanMonth7());
        existedInternetMeanMonth.setInternetMeanMonth8(internetMeanMonth.getInternetMeanMonth8());
        existedInternetMeanMonth.setInternetMeanMonth9(internetMeanMonth.getInternetMeanMonth9());
        existedInternetMeanMonth.setInternetMeanMonth10(internetMeanMonth.getInternetMeanMonth10());
        existedInternetMeanMonth.setInternetMeanMonth11(internetMeanMonth.getInternetMeanMonth11());
        existedInternetMeanMonth.setInternetMeanMonth12(internetMeanMonth.getInternetMeanMonth12());
        internetMeanMonthRepository.save(existedInternetMeanMonth);
    }
}
