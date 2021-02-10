package com.example.demo.controllers;

import com.example.demo.entities.Cluster;
import com.example.demo.entities.ClusterStats;
import com.example.demo.repositories.ClusterStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ClusterStatsController {
    private final ClusterStatsRepository clusterStatsRepository;
    private final ClusterController clusterController;

    @Autowired
    public ClusterStatsController(ClusterStatsRepository clusterStatsRepository, ClusterController clusterController) {
        this.clusterStatsRepository = clusterStatsRepository;
        this.clusterController = clusterController;
    }

    @CrossOrigin
    @GetMapping ("/cluster_stats")
    public List<ClusterStats> getAllClusterStats(){
        return clusterStatsRepository.findAll();
    }

    @CrossOrigin
    @GetMapping ("/cluster_stats/{id}")
    public Optional<ClusterStats> getClusterStatsById(@PathVariable("id") final int id){
        return clusterStatsRepository.findById(id);
    }

    @CrossOrigin
    @GetMapping ("/cluster_stats/cluster/{id}")
    public List<ClusterStats> getClusterStatsByClusterId(@PathVariable("id") final int id){
        if (clusterController.getClusterById(id).isPresent()) {
            return clusterStatsRepository.getClusterStatsByClusterId(id);
        }
        return null;
    }

    @CrossOrigin
    @PostMapping ("/cluster_stats")
    public void add(@RequestBody ClusterStats clusterStats){
        if (clusterController.getClusterById(clusterStats.getCluster().getId()).isPresent()){
            clusterStatsRepository.save(clusterStats);
        }
    }

    @CrossOrigin
    @PutMapping ("/cluster_stats/{id}")
    public void edit(@RequestBody ClusterStats clusterStats, @PathVariable("id") final int id){
        ClusterStats existingDetail;
        Cluster cluster;
        if (clusterController.getClusterById(clusterStats.getCluster().getId()).isPresent() &&
                getClusterStatsById(id).isPresent()){
            existingDetail = getClusterStatsById(id).get();
            cluster = clusterController.getClusterById(clusterStats.getCluster().getId()).get();
        }
        else {return;}
        existingDetail.setId(id);
        existingDetail.setCluster(cluster);
        existingDetail.setCategory(clusterStats.getCategory());
        existingDetail.setBiller(clusterStats.getBiller());
        existingDetail.setMean(clusterStats.getMean());
        existingDetail.setStandardDeviation(clusterStats.getStandardDeviation());
        clusterStatsRepository.save(existingDetail);
    }

    @CrossOrigin
    @DeleteMapping ("/cluster_stats/{id}")
    public void delete(@PathVariable("id") final int id){
        clusterStatsRepository.deleteById(id);
    }
}
