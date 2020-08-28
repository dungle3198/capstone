package com.example.demo.repositories;
import com.example.demo.entities.WaterMeanMonth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WaterMeanMonthRepository extends JpaRepository<WaterMeanMonth, Integer> {
}
