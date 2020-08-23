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
        UserMean existedUser = userMeanRepository.findById(id).get();
        existedUser.setId(userMean.getId());
        existedUser.setElectricity(userMean.getElectricity());
        existedUser.setGas(userMean.getGas());
        existedUser.setInternet(userMean.getInternet());
        existedUser.setWater(userMean.getWater());
        userMeanRepository.save(existedUser);
    }

    @CrossOrigin
    @PutMapping("/userstds/{id}")
    public void editStd(@RequestBody UserStd userStd, @PathVariable("id") final Integer id)
    {
        UserStd existedUser = userStdRepository.findById(id).get();
        existedUser.setId(userStd.getId());
        existedUser.setElectricity(userStd.getElectricity());
        existedUser.setGas(userStd.getGas());
        existedUser.setInternet(userStd.getInternet());
        existedUser.setWater(userStd.getWater());
        userStdRepository.save(existedUser);
    }

    @CrossOrigin
    @PostMapping("/bills")
    public void add(@RequestBody Bill newBill)
    {
        if (!getUserIdList().contains(newBill.getUser().getId())){
            throw new IndexOutOfBoundsException();
        }
        else {
            billRepository.save(newBill);
            Integer userId = newBill.getUser().getId();
            List<Bill> bills = getBillByUserId(userId);
            System.out.println(bills.toString());
            List<Double> amounts = new ArrayList<>();
            System.out.println("id" + userId);
            for (Bill bill : bills) {
                if (bill.getType().equalsIgnoreCase(newBill.getType())) {
                    amounts.add(bill.getAmount());
                }
            }
            System.out.println("amount" + amounts.size());
            if (!amounts.isEmpty()) {
                UserMean userMean = userMeanRepository.findById(userId).get();
                UserStd userStd = userStdRepository.findById(userId).get();
                Double mean = userMean.calculateMean(amounts, newBill.getType().toLowerCase());
                Double std = userStd.calculateStd(amounts, mean, newBill.getType().toLowerCase());
                System.out.println(mean);
                System.out.println(userMean.getGas());
                editMean(userMean, userId);
                editStd(userStd, userId);
            }
        }
    }

    @CrossOrigin
    @PutMapping("/bills/{id}")
    public void edit(@RequestBody Bill bill, @PathVariable ("id") final Integer id) {
        if (!getUserIdList().contains(bill.getUser().getId())) {
            throw new IndexOutOfBoundsException();
        } else {
            Bill existedBill = billRepository.findById(id).get();
            existedBill.setId(bill.getId());
            existedBill.setUser(bill.getUser());
            existedBill.setDate(bill.getDate());
            existedBill.setAmount(bill.getAmount());
            existedBill.setNumber(bill.getNumber());
            existedBill.setLocation(bill.getLocation());
            existedBill.setType(bill.getType());
            billRepository.save(existedBill);
        }
    }

    @CrossOrigin
    @DeleteMapping("/bills/{id}")
    public void delete (@PathVariable ("id") final Integer id)
    {
        billRepository.deleteById(id);
    }

}
