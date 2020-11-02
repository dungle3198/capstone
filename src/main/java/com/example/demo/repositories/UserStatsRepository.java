package com.example.demo.repositories;


import com.example.demo.entities.UserStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserStatsRepository extends JpaRepository<UserStats, Integer> {
    @Query("select u from UserStats u where u.user.id = ?1")
    List<UserStats> getUserStatsByUserId(int id);

    @Query("select u from UserStats u where u.user.id = ?1 and u.category = ?2 and u.biller = ?3")
    List<UserStats> getUserStatsByUserIdAndCategoryAndBiller(int id, String category, String biller);
}
