package com.example.demo.controllers;

import com.example.demo.entities.WaterMeanMonth;
import com.example.demo.repositories.WaterMeanMonthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @CrossOrigin
    @PutMapping("/water_mean_month/{id}")
    public void editWaterMeanMonth(@RequestBody WaterMeanMonth waterMeanMonth, @PathVariable("id") Integer id)
    {
        WaterMeanMonth existedWaterMeanMonth = waterMeanMonthRepository.findById(id).get();
        existedWaterMeanMonth.setId(waterMeanMonth.getId());
        existedWaterMeanMonth.setWaterMeanMonth1(waterMeanMonth.getWaterMeanMonth1());
        existedWaterMeanMonth.setWaterMeanMonth2(waterMeanMonth.getWaterMeanMonth2());
        existedWaterMeanMonth.setWaterMeanMonth3(waterMeanMonth.getWaterMeanMonth3());
        existedWaterMeanMonth.setWaterMeanMonth4(waterMeanMonth.getWaterMeanMonth4());
        existedWaterMeanMonth.setWaterMeanMonth5(waterMeanMonth.getWaterMeanMonth5());
        existedWaterMeanMonth.setWaterMeanMonth6(waterMeanMonth.getWaterMeanMonth6());
        existedWaterMeanMonth.setWaterMeanMonth7(waterMeanMonth.getWaterMeanMonth7());
        existedWaterMeanMonth.setWaterMeanMonth8(waterMeanMonth.getWaterMeanMonth8());
        existedWaterMeanMonth.setWaterMeanMonth9(waterMeanMonth.getWaterMeanMonth9());
        existedWaterMeanMonth.setWaterMeanMonth10(waterMeanMonth.getWaterMeanMonth10());
        existedWaterMeanMonth.setWaterMeanMonth11(waterMeanMonth.getWaterMeanMonth11());
        existedWaterMeanMonth.setWaterMeanMonth12(waterMeanMonth.getWaterMeanMonth12());
        waterMeanMonthRepository.save(existedWaterMeanMonth);
    }
}
