package com.example.demo.repositories;
import com.example.demo.entities.User;
import com.example.demo.entities.UserMean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Bill;

import java.util.List;

@Repository
public interface BillRepository  extends JpaRepository<Bill, Integer>{
    @Query("select e from Bill e where e.user.id = ?1")
    List<Bill> getBillsByUserId(int user_id);

    @Query("select amount from Bill e where e.user.id = :user_id and e.type = :type")
    List<Double> getBillAmountByUserIdAndType(@Param("user_id")int user_id,
                                              @Param("type")String type);

    @Query("select amount from Bill e where e.user.id = :user_id and e.type = :type and e.month = :month")
    List<Double> getBillAmountByUserIdAndTypeAndMonth(@Param("user_id")int user_id,
                                                      @Param("type")String type,
                                                      @Param("month")int month);

    @Query("select id from Bill e where e.user.id = :user_id and e.type = :type")
    List<Integer> getBillIdByUserIdAndType(@Param("user_id")int user_id,
                                           @Param("type")String type);

    @Query("select id from Bill e where e.user.id = :user_id and e.type = :type and e.month = :month")
    List<Integer> getBillIdByUserIdAndTypeAndMonth(@Param("user_id")int user_id,
                                                   @Param("type")String type,
                                                   @Param("month")int month);
}
