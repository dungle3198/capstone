package com.example.demo.controllers;

import com.example.demo.entities.GasMeanMonth;
import com.example.demo.repositories.GasMeanMonthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GasMeanMonthController {

    private final GasMeanMonthRepository gasMeanMonthRepository;

    @Autowired
    public GasMeanMonthController(GasMeanMonthRepository gasMeanMonthRepository) {
        this.gasMeanMonthRepository = gasMeanMonthRepository;
    }

    @CrossOrigin
    @GetMapping("/gas_mean_month")
    public List<GasMeanMonth> gasMeanMonthList(){return gasMeanMonthRepository.findAll();}
}
