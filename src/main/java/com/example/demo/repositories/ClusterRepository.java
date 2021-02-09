package com.example.demo.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Cluster;

import java.util.List;

@Repository
public interface ClusterRepository extends JpaRepository<Cluster, Integer>{
    @Query("select c from Cluster c where c.userId = ?1")
    List<Cluster> getClusterByPrimaryUserId(int id);
}
