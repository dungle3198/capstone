package com.example.demo.controllers;

import java.io.DataOutput;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import com.example.demo.entities.*;
import com.example.demo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class BillController {

    private final BillRepository billRepository;
    private final UserRepository userRepository;
    private final UserMeanRepository userMeanRepository;
    private final UserStdRepository userStdRepository;
    private final GasMeanMonthRepository gasMeanMonthRepository;
    private final InternetMeanMonthRepository internetMeanMonthRepository;
    private final ElectricityMeanMonthRepository electricityMeanMonthRepository;
    private final WaterMeanMonthRepository waterMeanMonthRepository;


    @Autowired
    public BillController(BillRepository billRepository, UserRepository userRepository, UserMeanRepository userMeanRepository, UserStdRepository userStdRepository, GasMeanMonthRepository gasMeanMonthRepository, InternetMeanMonthRepository internetMeanMonthRepository, ElectricityMeanMonthRepository electricityMeanMonthRepository, WaterMeanMonthRepository waterMeanMonthRepository) {
        this.billRepository = billRepository;
        this.userRepository = userRepository;
        this.userMeanRepository = userMeanRepository;
        this.userStdRepository = userStdRepository;
        this.gasMeanMonthRepository = gasMeanMonthRepository;
        this.internetMeanMonthRepository = internetMeanMonthRepository;
        this.electricityMeanMonthRepository = electricityMeanMonthRepository;
        this.waterMeanMonthRepository = waterMeanMonthRepository;
    }

    public List<Integer> getListOfUserId(){
        return userRepository.getListOfUserId();
    }

    public List<Double> getBillAmountByUserIdAndType(int user_id, String type){
        return billRepository.getBillAmountByUserIdAndType(user_id, type);
    }

    public List<Double> getBillAmountByUserIdAndTypeAndMonth(int user_id, String type, int month){
        return billRepository.getBillAmountByUserIdAndTypeAndMonth(user_id, type, month);
    }

    public List<Integer> getBillIdByUserIdAndType(int user_id, String type){
        return billRepository.getBillIdByUserIdAndType(user_id, type);
    }

    public void extractMeanMonth (Bill bill, List<Double> amounts){
        User user = bill.getUser();
        GasMeanMonth gasMeanMonth = user.getGasMeanMonth();
        InternetMeanMonth internetMeanMonth = user.getInternetMeanMonth();
        ElectricityMeanMonth electricityMeanMonth = user.getElectricityMeanMonth();
        WaterMeanMonth waterMeanMonth = user.getWaterMeanMonth();
        user.setMeanMonthType(amounts, bill.getType(), bill.getMonth());
        switch (bill.getType().toLowerCase()){
            case "internet":
                editInternetMeanMonth(internetMeanMonth, internetMeanMonth.getId());
                break;
            case "gas":
                editGasMeanMonth(gasMeanMonth, gasMeanMonth.getId());
                break;
            case "electricity":
                editElectricityMeanMonth(electricityMeanMonth, electricityMeanMonth.getId());
                break;
            case "water":
                editWaterMeanMonth(waterMeanMonth, waterMeanMonth.getId());
                break;
        }
    }

    public void extract (Bill bill, List<Double> amounts){
        User user = bill.getUser();
        UserMean userMean = user.getUserMean();
        UserStd userStd = user.getUserStd();
        double mean = userMean.calculateMean(amounts, bill.getType());
        userStd.calculateStd(amounts, mean, bill.getType());
        editMean(userMean, userMean.getId());
        editStd(userStd, userStd.getId());
    }

    public void extractAll(Bill bill){
        List<Double> amount_list1 = getBillAmountByUserIdAndType(bill.getUser().getId(), bill.getType());
        List<Double> amount_list2 = getBillAmountByUserIdAndTypeAndMonth(bill.getUser().getId(), bill.getType(), bill.getMonth());
        extract(bill, amount_list1);
        extractMeanMonth(bill, amount_list2);
    }

    @CrossOrigin
    @GetMapping("/bills")
    public List<Bill> bills(){
        return billRepository.findAll();
    }

    @CrossOrigin
    @GetMapping("/bills/{id}")
    public ResponseEntity<Bill> getBillById(@PathVariable("id") final int id)
    {
        Bill bill = billRepository.findById(id).get();
        return ResponseEntity.ok().body(bill);
    }

    @CrossOrigin
    @GetMapping("/bills/user/{id}")
    public List<Bill> getBillsByUserId(@PathVariable("id") final int user_id){
        return billRepository.getBillsByUserId(user_id);
    }

    @CrossOrigin
    @PostMapping("/bills")
    public void add(@RequestBody Bill newBill)
    {
        if (!getListOfUserId().contains(newBill.getUser().getId())){
            throw new IndexOutOfBoundsException();
        }
        else {
            User user = userRepository.findById(newBill.getUser().getId()).get();
            newBill.setUser(user);
            newBill.setMonth();
            billRepository.save(newBill);
            extractAll(newBill);
        }
    }

    public void compareAndExtract(Bill oldBill, Bill newBill){
        User user = oldBill.getUser();
        newBill.setMonth();
        oldBill.setMonth();
        List<Double> amount_list1 = getBillAmountByUserIdAndType(user.getId(), oldBill.getType());
        List<Double> amount_list2 = getBillAmountByUserIdAndTypeAndMonth(user.getId(), oldBill.getType(), oldBill.getMonth());
        List<Integer> bill_ids = getBillIdByUserIdAndType(user.getId(), oldBill.getType());
        int index = bill_ids.indexOf(newBill.getId());
        if (!newBill.getType().equalsIgnoreCase(oldBill.getType()) ||
                newBill.getAmount() != oldBill.getAmount() ||
                newBill.getUser().getId() != oldBill.getUser().getId())
        {
            amount_list1.remove(index);
            amount_list2.remove(index);
            extract(oldBill, amount_list1);
            extractMeanMonth(oldBill, amount_list2);
        }
        else if (newBill.getMonth() != oldBill.getMonth()){
            amount_list2.remove(index);
            extractMeanMonth(oldBill, amount_list2);
        }
    }

    @CrossOrigin
    @PutMapping("/bills/{id}")
    public void edit(@RequestBody Bill bill, @PathVariable ("id") final Integer id) throws ParseException {
        if (!getListOfUserId().contains(bill.getUser().getId())) {
            throw new IndexOutOfBoundsException();
        }
        else {
            Bill existedBill = billRepository.findById(id).get();
            User user = existedBill.getUser();
            compareAndExtract(existedBill, bill);
            existedBill.setId(bill.getId());
            existedBill.setUser(user);
            existedBill.setDate(bill.getDate());
            existedBill.setAmount(bill.getAmount());
            existedBill.setType(bill.getType());
            existedBill.setNumber(bill.getNumber());
            existedBill.setLocation(bill.getLocation());
            existedBill.setMonth();
            billRepository.save(existedBill);
            extractAll(existedBill);
        }
    }

    @CrossOrigin
    @DeleteMapping("/bills/{id}")
    public void delete (@PathVariable ("id") final Integer id)
    {
        billRepository.deleteById(id);
    }

    @CrossOrigin
    @PutMapping("/user_means/{id}")
    public void editMean(@RequestBody UserMean userMean, @PathVariable("id") final Integer id)
    {
        UserMean existedMean = userMeanRepository.findById(id).get();
        existedMean.setId(userMean.getId());
        existedMean.setElectricity(userMean.getElectricity());
        existedMean.setGas(userMean.getGas());
        existedMean.setInternet(userMean.getInternet());
        existedMean.setWater(userMean.getWater());
        userMeanRepository.save(existedMean);
    }

    @CrossOrigin
    @PutMapping("/user_stds/{id}")
    public void editStd(@RequestBody UserStd userStd, @PathVariable("id") final Integer id)
    {
        UserStd existedStd = userStdRepository.findById(id).get();
        existedStd.setId(userStd.getId());
        existedStd.setElectricity(userStd.getElectricity());
        existedStd.setGas(userStd.getGas());
        existedStd.setInternet(userStd.getInternet());
        existedStd.setWater(userStd.getWater());
        userStdRepository.save(existedStd);
    }

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
