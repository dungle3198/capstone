package com.example.demo.repositories;
import com.example.demo.entities.InternetMeanMonth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InternetMeanMonthRepository extends JpaRepository<InternetMeanMonth, Integer> {
}
