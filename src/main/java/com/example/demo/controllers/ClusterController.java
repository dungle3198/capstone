package com.example.demo.controllers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entities.Cluster;
import com.example.demo.repositories.ClusterRepository;

@RestController
public class ClusterController {

    private final ClusterRepository clusterRepository;

    @Autowired
    public ClusterController(ClusterRepository clusterRepository) {
        this.clusterRepository = clusterRepository;
    }

    @CrossOrigin
    @GetMapping("/clusters")
    public List<Cluster> clusters(){
        return clusterRepository.findAll();
    }

    @CrossOrigin
    @GetMapping("/clusters/{id}")
    public ResponseEntity<Cluster> getClusterById(@PathVariable("id") final int id)
    {
        Cluster cluster = clusterRepository.findById(id).get();
        return ResponseEntity.ok().body(cluster);
    }

    @CrossOrigin
    @PostMapping("/clusters")
    public void add(@RequestBody Cluster cluster)
    {
        clusterRepository.save(cluster);
    }

    @CrossOrigin
    @DeleteMapping("/clusters/{id}")
    public void delete (@PathVariable("id") final Integer id)
    {
        clusterRepository.deleteById(id);
    }

}
