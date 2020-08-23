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
    @GetMapping("/ClusterMeans")
    public List<ClusterMean> ClusterMeans(){
        return clusterMeanRepository.findAll();
    }

    @CrossOrigin
    @GetMapping("/ClusterMeans/{id}")
    public ResponseEntity<ClusterMean> getClusterMeanById(@PathVariable("id") final int ClusterMeanId)
    {
        ClusterMean ClusterMean = clusterMeanRepository.findById(ClusterMeanId).get();
        return ResponseEntity.ok().body(ClusterMean);
    }

    @CrossOrigin
    @PostMapping("/ClusterMeans")
    public void add(@RequestBody ClusterMean ClusterMean)
    {
        clusterMeanRepository.save(ClusterMean);
    }

    @CrossOrigin
    @PutMapping("/ClusterMeans/{id}")
    public void edit(@RequestBody ClusterMean ClusterMean, @PathVariable("id") final Integer id)
    {
        ClusterMean existedClusterMean = clusterMeanRepository.findById(id).get();
        existedClusterMean.setId(ClusterMean.getId());
        existedClusterMean.setId(ClusterMean.getId());
        existedClusterMean.setElectricity(ClusterMean.getElectricity());
        existedClusterMean.setGas(ClusterMean.getGas());
        existedClusterMean.setInternet(ClusterMean.getInternet());
        existedClusterMean.setWater(ClusterMean.getWater());
        clusterMeanRepository.save(existedClusterMean);
    }

    @CrossOrigin
    @DeleteMapping("/ClusterMeans/{id}")
    public void delete (@PathVariable("id") final Integer id)
    {
        clusterMeanRepository.deleteById(id);
    }

}
