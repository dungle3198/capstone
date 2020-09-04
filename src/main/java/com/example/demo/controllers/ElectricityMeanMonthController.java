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
        existedElectricityMeanMonth.setElectricity_mm1(electricityMeanMonth.getElectricity_mm1());
        existedElectricityMeanMonth.setElectricity_mm2(electricityMeanMonth.getElectricity_mm2());
        existedElectricityMeanMonth.setElectricity_mm3(electricityMeanMonth.getElectricity_mm3());
        existedElectricityMeanMonth.setElectricity_mm4(electricityMeanMonth.getElectricity_mm4());
        existedElectricityMeanMonth.setElectricity_mm5(electricityMeanMonth.getElectricity_mm5());
        existedElectricityMeanMonth.setElectricity_mm6(electricityMeanMonth.getElectricity_mm6());
        existedElectricityMeanMonth.setElectricity_mm7(electricityMeanMonth.getElectricity_mm7());
        existedElectricityMeanMonth.setElectricity_mm8(electricityMeanMonth.getElectricity_mm8());
        existedElectricityMeanMonth.setElectricity_mm9(electricityMeanMonth.getElectricity_mm9());
        existedElectricityMeanMonth.setElectricity_mm10(electricityMeanMonth.getElectricity_mm10());
        existedElectricityMeanMonth.setElectricity_mm11(electricityMeanMonth.getElectricity_mm11());
        existedElectricityMeanMonth.setElectricity_mm12(electricityMeanMonth.getElectricity_mm12());
        electricityMeanMonthRepository.save(existedElectricityMeanMonth);
    }
}
