package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entities.Bill;
import com.example.demo.entities.User;
import com.example.demo.entities.UserMean;
import com.example.demo.entities.UserStd;
import com.example.demo.repositories.BillRepository;
import com.example.demo.repositories.UserMeanRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.repositories.UserStdRepository;



@RestController
public class BillController {

    private final BillRepository billRepository;
    private final UserRepository userRepository;
    private final UserMeanRepository userMeanRepository;
    private final UserStdRepository userStdRepository;


    @Autowired
    public BillController(BillRepository billRepository, UserRepository userRepository, UserMeanRepository userMeanRepository, UserStdRepository userStdRepository) {
        this.billRepository = billRepository;
        this.userRepository = userRepository;
        this.userMeanRepository = userMeanRepository;
        this.userStdRepository = userStdRepository;
    }

    public List<Integer> getUserIdList(){
        List<Integer> user_ids = new ArrayList<>();
        for (User user: userRepository.findAll()){
            user_ids.add(user.getId());
        }
        return user_ids;
    }

    @CrossOrigin
    @GetMapping("/bills")
    public List<Bill> bills(){
        return billRepository.findAll();
    }

    @CrossOrigin
    @GetMapping("/bills/{id}")
    public ResponseEntity<Bill> getBillById(@PathVariable("id") final int billId)
    {
        Bill bill = billRepository.findById(billId).get();
        return ResponseEntity.ok().body(bill);
    }

    @CrossOrigin
    @GetMapping("/bills/user/{id}")
    public List<Bill> getBillByUserId(@PathVariable("id") final int userId){
        User user = userRepository.findById(userId).get();
        return user.getBills();
    }
    @CrossOrigin
    @PutMapping("/usermeans/{id}")
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
    @PutMapping("/userstds/{id}")
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
    @PostMapping("/bills")
    public void add(@RequestBody Bill newBill)
    {
        if (!getUserIdList().contains(newBill.getUser().getId())){
            throw new IndexOutOfBoundsException();
        }
        else {
            User user = userRepository.findById(newBill.getUser().getId()).get();
            newBill.setUser(user);
            billRepository.save(newBill);
            UserMean userMean = (UserMean) newBill.extract().get(0);
            UserStd userStd = (UserStd) newBill.extract().get(1);
            editMean(userMean, userMean.getId());
            editStd(userStd, userStd.getId());
        }
    }

    @CrossOrigin
    @PutMapping("/bills/{id}")
    public void edit(@RequestBody Bill bill, @PathVariable ("id") final Integer id) {
        if (!getUserIdList().contains(bill.getUser().getId())) {
            throw new IndexOutOfBoundsException();
        } else {
            Bill existedBill = billRepository.findById(id).get();
            User user = userRepository.findById(bill.getUser().getId()).get();
            existedBill.setId(bill.getId());
            existedBill.setUser(user);
            existedBill.setDate(bill.getDate());
            existedBill.setAmount(bill.getAmount());
            existedBill.setNumber(bill.getNumber());
            existedBill.setLocation(bill.getLocation());
            existedBill.setType(bill.getType());
            billRepository.save(existedBill);
            UserMean userMean = (UserMean) existedBill.extract().get(0);
            UserStd userStd = (UserStd) existedBill.extract().get(1);
            editMean(userMean, userMean.getId());
            editStd(userStd, userStd.getId());
        }
    }

    @CrossOrigin
    @DeleteMapping("/bills/{id}")
    public void delete (@PathVariable ("id") final Integer id)
    {
        billRepository.deleteById(id);
    }

}
