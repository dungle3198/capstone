package com.example.demo.controllers;

import java.util.List;


import com.example.demo.repositories.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entities.Bill;
import com.example.demo.repositories.BillRepository;

@RestController
public class BillController {

    private final BillRepository billRepository;

    @Autowired
    public BillController(BillRepository billRepository) {
        this.billRepository = billRepository;
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
    @PostMapping("/bills")
    public void add(@RequestBody Bill bill)
    {
        billRepository.save(bill);
    }

    @CrossOrigin
    @PutMapping("/bills/{id}")
    public void edit(@RequestBody Bill bill, @PathVariable ("id") final Integer id)
    {
        Bill existedBill = billRepository.findById(id).get();
        existedBill.setId(bill.getId());
        existedBill.setUserId(bill.getUserId());
        existedBill.setDate(bill.getDate());
        existedBill.setAmount(bill.getAmount());
        existedBill.setNumber(bill.getNumber());
        existedBill.setLocation(bill.getLocation());
        existedBill.setType(bill.getType());
        billRepository.save(existedBill);
    }

    @CrossOrigin
    @DeleteMapping("/bills/{id}")
    public void delete (@PathVariable ("id") final Integer id)
    {
        billRepository.deleteById(id);
    }

}
