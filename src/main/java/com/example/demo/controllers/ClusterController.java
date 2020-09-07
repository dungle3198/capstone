package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import com.example.demo.entities.User;
import com.example.demo.entities.UserMean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entities.Cluster;
import com.example.demo.repositories.ClusterRepository;

@RestController
public class ClusterController {

    private final ClusterRepository clusterRepository;
    private final UserController userController;

    @Autowired
    public ClusterController(ClusterRepository clusterRepository, UserController userController) {
        this.clusterRepository = clusterRepository;
        this.userController = userController;
    }

    public List<User> createCluster(User userA, List<User> users){
        List<User> listOfUsers = new ArrayList<>();
        List<Double> distances = new ArrayList<>();
        Cluster cluster = new Cluster();
        User userB;
        User userC;
        users.remove(userA);
        for (User user : users) {
            if (!user.isNewUser()) {
                UserMean userMean = user.getUserMean();
                UserMean userMeanA = userA.getUserMean();
                double electricityDistance = Math.pow(userMean.getElectricity() - userMeanA.getElectricity(), 2);
                double waterDistance = Math.pow(userMean.getWater() - userMeanA.getWater(), 2);
                double gasDistance = Math.pow(userMean.getGas() - userMeanA.getGas(), 2);
                double internetDistance = Math.pow(userMean.getInternet() - userMeanA.getInternet(), 2);
                double distance = Math.sqrt(electricityDistance + waterDistance + gasDistance + internetDistance);
                distances.add(distance);
            }
        }
        userA.setCluster(cluster);
        listOfUsers.add(userA);
        cluster.getUsers().add(userA);

        int index1 = distances.indexOf(Collections.min(distances));
        userB = users.get(index1);
        userB.setCluster(cluster);
        listOfUsers.add(userB);
        cluster.getUsers().add(userB);
        distances.remove(index1);
        users.remove(index1);

        int index2 = distances.indexOf(Collections.min(distances));
        userC = users.get(index2);
        userC.setCluster(cluster);
        listOfUsers.add(userC);
        cluster.getUsers().add(userC);

        add(cluster);
        cluster.calculateCluster();
        edit(cluster, cluster.getId());
        return listOfUsers;
    }

    @CrossOrigin
    @GetMapping("/clusters")
    public List<Cluster> clusters() {
//        if (clusterRepository.findAll().isEmpty()) {
//            List<User> users = userController.users();
//            for (User user : users) {
//                List<User> listOfUsers = createCluster(user, users);
//                users.removeAll(listOfUsers);
//                if (users.isEmpty()) {
//                    break;
//                }
//            }
//        }
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
    @PutMapping("/clusters/{id}")
    public void edit(@RequestBody Cluster cluster, @PathVariable("id") final int id)
    {
        Cluster existedCluster = clusterRepository.findById(id).get();
        existedCluster.setId(cluster.getId());
        existedCluster.setElectricityClusterMean(cluster.getElectricityClusterMean());
        existedCluster.setInternetClusterMean(cluster.getInternetClusterMean());
        existedCluster.setWaterClusterMean(cluster.getWaterClusterMean());
        existedCluster.setGasClusterMean(cluster.getGasClusterMean());
        existedCluster.setElectricityClusterStd(cluster.getElectricityClusterStd());
        existedCluster.setInternetClusterStd(cluster.getInternetClusterStd());
        existedCluster.setWaterClusterStd(cluster.getWaterClusterStd());
        existedCluster.setGasClusterStd(cluster.getGasClusterStd());
        clusterRepository.save(existedCluster);
    }

    @CrossOrigin
    @DeleteMapping("/clusters/{id}")
    public void delete (@PathVariable("id") final Integer id)
    {
        clusterRepository.deleteById(id);
    }

}
