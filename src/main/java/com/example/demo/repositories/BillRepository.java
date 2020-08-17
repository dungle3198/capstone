package com.example.demo.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Bill;
@Repository
public interface BillRepository  extends JpaRepository<Bill, Integer>{
}
