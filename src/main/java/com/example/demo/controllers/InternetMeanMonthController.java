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
        existedInternetMeanMonth.setInternet_mm1(internetMeanMonth.getInternet_mm1());
        existedInternetMeanMonth.setInternet_mm2(internetMeanMonth.getInternet_mm2());
        existedInternetMeanMonth.setInternet_mm3(internetMeanMonth.getInternet_mm3());
        existedInternetMeanMonth.setInternet_mm4(internetMeanMonth.getInternet_mm4());
        existedInternetMeanMonth.setInternet_mm5(internetMeanMonth.getInternet_mm5());
        existedInternetMeanMonth.setInternet_mm6(internetMeanMonth.getInternet_mm6());
        existedInternetMeanMonth.setInternet_mm7(internetMeanMonth.getInternet_mm7());
        existedInternetMeanMonth.setInternet_mm8(internetMeanMonth.getInternet_mm8());
        existedInternetMeanMonth.setInternet_mm9(internetMeanMonth.getInternet_mm9());
        existedInternetMeanMonth.setInternet_mm10(internetMeanMonth.getInternet_mm10());
        existedInternetMeanMonth.setInternet_mm11(internetMeanMonth.getInternet_mm11());
        existedInternetMeanMonth.setInternet_mm12(internetMeanMonth.getInternet_mm12());
        internetMeanMonthRepository.save(existedInternetMeanMonth);
    }
}
