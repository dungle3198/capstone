package com.example.demo.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.ClusterMean;
@Repository
public interface ClusterMeanRepository  extends JpaRepository<ClusterMean, Integer>{
}