package com.example.demo.controllers;

import com.example.demo.entities.Cluster;
import com.example.demo.entities.ClusterDetail;
import com.example.demo.entities.User;
import com.example.demo.repositories.ClusterDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ClusterDetailController {
    private final ClusterDetailRepository clusterDetailRepository;
    private final ClusterController clusterController;
    private final UserController userController;

    @Autowired
    public ClusterDetailController(ClusterDetailRepository clusterDetailRepository, ClusterController clusterController, UserController userController) {
        this.clusterDetailRepository = clusterDetailRepository;
        this.clusterController = clusterController;
        this.userController = userController;
    }

    @CrossOrigin
    @GetMapping ("/cluster_detail")
    public List<ClusterDetail> getAllClusterDetail(){
        return clusterDetailRepository.findAll();
    }

    @CrossOrigin
    @GetMapping ("/cluster_detail/{id}")
    public Optional<ClusterDetail> getClusterDetailById(@PathVariable("id") final int id){
        return clusterDetailRepository.findById(id);
    }

    @CrossOrigin
    @GetMapping ("/cluster_detail/cluster/{id}")
    public List<ClusterDetail> getClusterDetailByClusterId(@PathVariable("id") final int id){
        return clusterDetailRepository.getClusterDetailsByClusterId(id);
    }

    @CrossOrigin
    @GetMapping ("/cluster_detail/user/{id}")
    public List<ClusterDetail> getClusterDetailByUserId(@PathVariable("id") final int id){
        return clusterDetailRepository.getClusterDetailsByUserId(id);
    }

    @CrossOrigin
    @PostMapping ("/cluster_detail")
    public void add(@RequestBody ClusterDetail clusterDetail){
        if (clusterController.getClusterById(clusterDetail.getCluster().getId()).isPresent()){
            clusterDetailRepository.save(clusterDetail);
        }
    }

    @CrossOrigin
    @PutMapping ("/cluster_detail/{id}")
    public void edit(@RequestBody ClusterDetail clusterDetail, @PathVariable("id") final int id){
        ClusterDetail existingDetail;
        Cluster cluster;
        User user;
        if (clusterController.getClusterById(clusterDetail.getCluster().getId()).isPresent() &&
                userController.getUserById(clusterDetail.getUser().getId()).isPresent() &&
                getClusterDetailById(id).isPresent()){
            existingDetail = getClusterDetailById(id).get();
            cluster = clusterController.getClusterById(clusterDetail.getCluster().getId()).get();
            user = userController.getUserById(clusterDetail.getUser().getId()).get();
        }
        else {return;}
        existingDetail.setId(id);
        existingDetail.setCluster(cluster);
        existingDetail.setUser(user);
        clusterDetailRepository.save(existingDetail);
    }

    @CrossOrigin
    @DeleteMapping ("/cluster_detail/{id}")
    public void delete(@PathVariable("id") final int id){
        clusterDetailRepository.deleteById(id);
    }
}
