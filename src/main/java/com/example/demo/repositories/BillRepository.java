package com.example.demo.repositories;
import com.example.demo.entities.UserMean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Bill;

import java.util.List;

@Repository
public interface BillRepository  extends JpaRepository<Bill, Integer>{
    @Query("select b from bill e where e.user_id = ?1")
    List<Bill> getBillByUserId(String user_id);
    @Query("select b from bill e where e.user_id = :user_id and e.type = :type")
    List<Bill> getBillByUserIdAndType(@Param("user_id")String user_id,@Param("type")String type);
}
