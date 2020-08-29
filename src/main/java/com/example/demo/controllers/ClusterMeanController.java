package com.example.demo.controllers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entities.ClusterMean;
import com.example.demo.repositories.ClusterMeanRepository;

@RestController
public class ClusterMeanController {

    private final ClusterMeanRepository clusterMeanRepository;

    @Autowired
    public ClusterMeanController(ClusterMeanRepository clusterMeanRepository) {
        this.clusterMeanRepository = clusterMeanRepository;
    }

    @CrossOrigin
    @GetMapping("/cluster_means")
    public List<ClusterMean> ClusterMeans(){
        return clusterMeanRepository.findAll();
    }

    @CrossOrigin
    @GetMapping("/cluster_means/{id}")
    public ResponseEntity<ClusterMean> getClusterMeanById(@PathVariable("id") final int id)
    {
        ClusterMean clusterMean = clusterMeanRepository.findById(id).get();
        return ResponseEntity.ok().body(clusterMean);
    }

    @CrossOrigin
    @PostMapping("/cluster_means")
    public void add(@RequestBody ClusterMean clusterMean)
    {
        clusterMeanRepository.save(clusterMean);
    }

    @CrossOrigin
    @PutMapping("/cluster_means/{id}")
    public void edit(@RequestBody ClusterMean clusterMean, @PathVariable("id") final Integer id)
    {
        ClusterMean existedClusterMean = clusterMeanRepository.findById(id).get();
        existedClusterMean.setId(clusterMean.getId());
        existedClusterMean.setElectricity(clusterMean.getElectricity());
        existedClusterMean.setGas(clusterMean.getGas());
        existedClusterMean.setInternet(clusterMean.getInternet());
        existedClusterMean.setWater(clusterMean.getWater());
        clusterMeanRepository.save(existedClusterMean);
    }

    @CrossOrigin
    @DeleteMapping("/cluster_means/{id}")
    public void delete (@PathVariable("id") final Integer id)
    {
        clusterMeanRepository.deleteById(id);
    }

}
