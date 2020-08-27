package com.example.demo.repositories;
import com.example.demo.entities.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.example.demo.entities.UserMean;
@Repository
public interface UserMeanRepository  extends JpaRepository<UserMean, Integer>{
}
