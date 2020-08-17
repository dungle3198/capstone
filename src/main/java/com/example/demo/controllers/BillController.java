package com.example.demo.controllers;

import java.util.List;


import com.example.demo.repositories.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/bills")
    public List<Bill> bills(){
        return billRepository.findAll();
    }
    @GetMapping("/bills/{id}")
    public ResponseEntity<Bill> getBillById(@PathVariable(value = "1") int userId)
    {
        Bill user = billRepository.findById(userId).get();
        return ResponseEntity.ok().body(user);
    }
    @PostMapping("/bills")
    public void add(@RequestBody Bill bill)
    {
        billRepository.save(bill);
    }
    @PutMapping("/bills/{id}")
    public void edit(@RequestBody Bill bill, @PathVariable Integer id)
    {
        Bill existedUser = billRepository.findById(id).get();
        billRepository.save(existedUser);
    }
    @DeleteMapping("/bills/{id}")
    public void delete (@PathVariable Integer id)
    {
        billRepository.deleteById(id);
    }

}
