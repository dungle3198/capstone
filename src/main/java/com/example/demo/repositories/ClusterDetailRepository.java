package com.example.demo.repositories;

import com.example.demo.entities.ClusterDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClusterDetailRepository extends JpaRepository<ClusterDetail, Integer> {
    @Query("select c from ClusterDetail c where c.cluster.id = ?1")
    List<ClusterDetail> getClusterDetailsByClusterId(int id);

    @Query("select c from ClusterDetail c where c.cluster.id = ?1 and c.category = ?2 and c.biller = ?3")
    List<ClusterDetail> getClusterDetailsByClusterIdAndCategoryAndBiller(int id, String category, String biller);
}
