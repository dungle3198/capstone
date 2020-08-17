package com.example.demo.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.UserMean;
@Repository
public interface UserMeanRepository  extends JpaRepository<UserMean, Integer>{
}
