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
        existedGasMeanMonth.setGasMeanMonth1(gasMeanMonth.getGasMeanMonth1());
        existedGasMeanMonth.setGasMeanMonth2(gasMeanMonth.getGasMeanMonth2());
        existedGasMeanMonth.setGasMeanMonth3(gasMeanMonth.getGasMeanMonth3());
        existedGasMeanMonth.setGasMeanMonth4(gasMeanMonth.getGasMeanMonth4());
        existedGasMeanMonth.setGasMeanMonth5(gasMeanMonth.getGasMeanMonth5());
        existedGasMeanMonth.setGasMeanMonth6(gasMeanMonth.getGasMeanMonth6());
        existedGasMeanMonth.setGasMeanMonth7(gasMeanMonth.getGasMeanMonth7());
        existedGasMeanMonth.setGasMeanMonth8(gasMeanMonth.getGasMeanMonth8());
        existedGasMeanMonth.setGasMeanMonth9(gasMeanMonth.getGasMeanMonth9());
        existedGasMeanMonth.setGasMeanMonth10(gasMeanMonth.getGasMeanMonth10());
        existedGasMeanMonth.setGasMeanMonth11(gasMeanMonth.getGasMeanMonth11());
        existedGasMeanMonth.setGasMeanMonth12(gasMeanMonth.getGasMeanMonth12());
        gasMeanMonthRepository.save(existedGasMeanMonth);
    }
}
