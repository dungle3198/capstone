package com.example.demo.controllers;

import com.example.demo.entities.Cluster;
import com.example.demo.entities.ClusterDetail;
import com.example.demo.repositories.ClusterDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ClusterDetailController {
    private final ClusterDetailRepository clusterDetailRepository;
    private final ClusterController clusterController;

    @Autowired
    public ClusterDetailController(ClusterDetailRepository clusterDetailRepository, ClusterController clusterController) {
        this.clusterDetailRepository = clusterDetailRepository;
        this.clusterController = clusterController;
    }

    @CrossOrigin
    @GetMapping ("/cluster_details")
    public List<ClusterDetail> getAllClusterDetails(){
        return clusterDetailRepository.findAll();
    }

    @CrossOrigin
    @GetMapping ("/cluster_details/{id}")
    public Optional<ClusterDetail> getClusterDetailsById(@PathVariable("id") final int id){
        return clusterDetailRepository.findById(id);
    }

    @CrossOrigin
    @GetMapping ("/cluster_details/cluster/{id}")
    public List<ClusterDetail> getClusterDetailsByClusterId(@PathVariable("id") final int id){
        return clusterDetailRepository.getClusterDetailsByClusterId(id);
    }

    @CrossOrigin
    @PostMapping ("/cluster_details")
    public void add(@RequestBody ClusterDetail clusterDetail){
        if (clusterController.getClusterById(clusterDetail.getCluster().getId()).isPresent()){
            clusterDetailRepository.save(clusterDetail);
        }
    }

    @CrossOrigin
    @PutMapping ("/cluster_details/{id}")
    public void edit(@RequestBody ClusterDetail clusterDetail, @PathVariable("id") final int id){
        ClusterDetail existingDetail;
        Cluster cluster;
        if (clusterController.getClusterById(clusterDetail.getCluster().getId()).isPresent() &&
                getClusterDetailsById(id).isPresent()){
            existingDetail = getClusterDetailsById(id).get();
            cluster = clusterController.getClusterById(clusterDetail.getCluster().getId()).get();
        }
        else {return;}
        existingDetail.setId(clusterDetail.getId());
        existingDetail.setCluster(cluster);
        existingDetail.setCategory(clusterDetail.getCategory());
        existingDetail.setBiller(clusterDetail.getBiller());
        existingDetail.setMean(clusterDetail.getMean());
        existingDetail.setStandardDeviation(clusterDetail.getStandardDeviation());
        clusterDetailRepository.save(existingDetail);
    }

    @CrossOrigin
    @DeleteMapping ("/cluster_details/{id}")
    public void delete(@PathVariable("id") final int id){
        clusterDetailRepository.deleteById(id);
    }
}
