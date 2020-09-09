package com.example.demo.controllers;

import com.example.demo.entities.ElectricityMeanMonth;
import com.example.demo.repositories.ElectricityMeanMonthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @CrossOrigin
    @PutMapping("/electricity_mean_month/{id}")
    public void editElectricityMeanMonth(@RequestBody ElectricityMeanMonth electricityMeanMonth, @PathVariable("id") Integer id)
    {
        ElectricityMeanMonth existedElectricityMeanMonth = electricityMeanMonthRepository.findById(id).get();
        existedElectricityMeanMonth.setId(electricityMeanMonth.getId());
        existedElectricityMeanMonth.setElectricityMeanMonth1(electricityMeanMonth.getElectricityMeanMonth1());
        existedElectricityMeanMonth.setElectricityMeanMonth2(electricityMeanMonth.getElectricityMeanMonth2());
        existedElectricityMeanMonth.setElectricityMeanMonth3(electricityMeanMonth.getElectricityMeanMonth3());
        existedElectricityMeanMonth.setElectricityMeanMonth4(electricityMeanMonth.getElectricityMeanMonth4());
        existedElectricityMeanMonth.setElectricityMeanMonth5(electricityMeanMonth.getElectricityMeanMonth5());
        existedElectricityMeanMonth.setElectricityMeanMonth6(electricityMeanMonth.getElectricityMeanMonth6());
        existedElectricityMeanMonth.setElectricityMeanMonth7(electricityMeanMonth.getElectricityMeanMonth7());
        existedElectricityMeanMonth.setElectricityMeanMonth8(electricityMeanMonth.getElectricityMeanMonth8());
        existedElectricityMeanMonth.setElectricityMeanMonth9(electricityMeanMonth.getElectricityMeanMonth9());
        existedElectricityMeanMonth.setElectricityMeanMonth10(electricityMeanMonth.getElectricityMeanMonth10());
        existedElectricityMeanMonth.setElectricityMeanMonth11(electricityMeanMonth.getElectricityMeanMonth11());
        existedElectricityMeanMonth.setElectricityMeanMonth12(electricityMeanMonth.getElectricityMeanMonth12());
        electricityMeanMonthRepository.save(existedElectricityMeanMonth);
    }
}
