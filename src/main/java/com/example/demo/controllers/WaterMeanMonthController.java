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
        existedWaterMeanMonth.setWater_mm1(waterMeanMonth.getWater_mm1());
        existedWaterMeanMonth.setWater_mm2(waterMeanMonth.getWater_mm2());
        existedWaterMeanMonth.setWater_mm3(waterMeanMonth.getWater_mm3());
        existedWaterMeanMonth.setWater_mm4(waterMeanMonth.getWater_mm4());
        existedWaterMeanMonth.setWater_mm5(waterMeanMonth.getWater_mm5());
        existedWaterMeanMonth.setWater_mm6(waterMeanMonth.getWater_mm6());
        existedWaterMeanMonth.setWater_mm7(waterMeanMonth.getWater_mm7());
        existedWaterMeanMonth.setWater_mm8(waterMeanMonth.getWater_mm8());
        existedWaterMeanMonth.setWater_mm9(waterMeanMonth.getWater_mm9());
        existedWaterMeanMonth.setWater_mm10(waterMeanMonth.getWater_mm10());
        existedWaterMeanMonth.setWater_mm11(waterMeanMonth.getWater_mm11());
        existedWaterMeanMonth.setWater_mm12(waterMeanMonth.getWater_mm12());
        waterMeanMonthRepository.save(existedWaterMeanMonth);
    }
}
