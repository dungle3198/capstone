package com.example.demo.controllers;

import com.example.demo.entities.GasMeanMonth;
import com.example.demo.repositories.GasMeanMonthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @CrossOrigin
    @PutMapping("/gas_mean_month/{id}")
    public void editGasMeanMonth(@RequestBody GasMeanMonth gasMeanMonth, @PathVariable("id") Integer id)
    {
        GasMeanMonth existedGasMeanMonth = gasMeanMonthRepository.findById(id).get();
        existedGasMeanMonth.setId(gasMeanMonth.getId());
        existedGasMeanMonth.setGas_mm1(gasMeanMonth.getGas_mm1());
        existedGasMeanMonth.setGas_mm2(gasMeanMonth.getGas_mm2());
        existedGasMeanMonth.setGas_mm3(gasMeanMonth.getGas_mm3());
        existedGasMeanMonth.setGas_mm4(gasMeanMonth.getGas_mm4());
        existedGasMeanMonth.setGas_mm5(gasMeanMonth.getGas_mm5());
        existedGasMeanMonth.setGas_mm6(gasMeanMonth.getGas_mm6());
        existedGasMeanMonth.setGas_mm7(gasMeanMonth.getGas_mm7());
        existedGasMeanMonth.setGas_mm8(gasMeanMonth.getGas_mm8());
        existedGasMeanMonth.setGas_mm9(gasMeanMonth.getGas_mm9());
        existedGasMeanMonth.setGas_mm10(gasMeanMonth.getGas_mm10());
        existedGasMeanMonth.setGas_mm11(gasMeanMonth.getGas_mm11());
        existedGasMeanMonth.setGas_mm12(gasMeanMonth.getGas_mm12());
        gasMeanMonthRepository.save(existedGasMeanMonth);
    }
}
