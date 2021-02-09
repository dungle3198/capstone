package com.example.demo.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.ClusterDetail;

import java.util.List;

@Repository
public interface ClusterDetailRepository extends JpaRepository<ClusterDetail, Integer> {
    @Query("select c from ClusterDetail c where c.cluster.id = ?1")
    List<ClusterDetail> getClusterDetailsByClusterId(int id);

    @Query("select c from ClusterDetail c where c.user.id = ?1")
    List<ClusterDetail> getClusterDetailsByUserId(int id);
}
