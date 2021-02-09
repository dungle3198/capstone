package com.example.demo.repositories;

import com.example.demo.entities.ClusterStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClusterStatsRepository extends JpaRepository<ClusterStats, Integer> {
    @Query("select c from ClusterStats c where c.cluster.id = ?1")
    List<ClusterStats> getClusterStatsByClusterId(int id);

    @Query("select c from ClusterStats c where c.cluster.id = ?1 and c.category = ?2 and c.biller = ?3")
    List<ClusterStats> getClusterStatsByClusterIdAndCategoryAndBiller(int id, String category, String biller);
}
