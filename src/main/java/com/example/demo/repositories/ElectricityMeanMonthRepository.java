package com.example.demo.repositories;
import com.example.demo.entities.ElectricityMeanMonth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElectricityMeanMonthRepository extends JpaRepository<ElectricityMeanMonth, Integer> {
}
