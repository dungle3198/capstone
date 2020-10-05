package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.demo.entities.*;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.repositories.ClusterRepository;

@RestController
public class ClusterController {

    private final ClusterRepository clusterRepository;
    private final UserController userController;
    private final UserRepository userRepository;

    @Autowired
    public ClusterController(ClusterRepository clusterRepository, UserController userController, UserRepository userRepository) {
        this.clusterRepository = clusterRepository;
        this.userController = userController;
        this.userRepository = userRepository;
    }

    public List<String> createUtilityList(User user){
        List<Bill> bills = user.getBills();
        List<String> utilityList = new ArrayList<>();
        for (Bill bill : bills){
            if (!utilityList.contains(bill.getType().toLowerCase())){
                utilityList.add(bill.getType().toLowerCase());
            }
        }
        System.out.println(utilityList);
        return utilityList;
    }

    public List<User> createCluster(User userA, List<User> users){
        List<User> userList = new ArrayList<>();
        List<Double> distances = new ArrayList<>();
        Cluster cluster = new Cluster();
        User userB;
        User userC;
        users.remove(userA);

        userA.setCluster(cluster);
        cluster.getUsers().add(userA);
        userList.add(userA);
        if (users.isEmpty()){
            add(cluster);
            cluster.calculateCluster();
            edit(cluster, cluster.getId());
            return userList;
        }

        for (User user : users) {
            double distance = Double.MAX_VALUE;
            UserMean userMean = user.getUserMean();
            UserMean userMeanA = userA.getUserMean();
            UserStd userStd = user.getUserStd();
            UserStd userStdA = userA.getUserStd();
            double electricityDistance = Math.pow(userMean.getElectricity() - userMeanA.getElectricity(), 2)
                    + Math.pow(userStd.getElectricity() - userStdA.getElectricity(), 2);
            double waterDistance = Math.pow(userMean.getWater() - userMeanA.getWater(), 2)
                    + Math.pow(userStd.getWater() - userStdA.getWater(), 2);
            double gasDistance = Math.pow(userMean.getGas() - userMeanA.getGas(), 2)
                    + Math.pow(userStd.getGas() - userStdA.getGas(), 2);
            double internetDistance = Math.pow(userMean.getInternet() - userMeanA.getInternet(), 2)
                    + Math.pow(userStd.getInternet() - userStdA.getInternet(), 2);

            List<String> utilityList = createUtilityList(user);
            List<String> utilityListA = createUtilityList(userA);
            utilityList.addAll(utilityListA);
            System.out.println(utilityList);
            double sumOfDistance = 0;
            int numberOfPoints = 0;

            if (Collections.frequency(utilityList, "electricity") == 2) {
                sumOfDistance += electricityDistance;
                numberOfPoints += 2;
            }

            if (Collections.frequency(utilityList, "phone and internet") == 2) {
                sumOfDistance += internetDistance;
                numberOfPoints += 2;
            }

            if (Collections.frequency(utilityList, "gas") == 2) {
                sumOfDistance += gasDistance;
                numberOfPoints += 2;
            }

            if (Collections.frequency(utilityList, "water") == 2) {
                sumOfDistance += waterDistance;
                numberOfPoints += 2;
            }

            if (sumOfDistance != 0) {
                distance = (1 / Math.sqrt(numberOfPoints)) * Math.sqrt(sumOfDistance);
                System.out.println(numberOfPoints);
            }
            distances.add(distance);
            System.out.println(distance);
        }

        int index1 = distances.indexOf(Collections.min(distances));
        userB = users.get(index1);
        userB.setCluster(cluster);
        cluster.getUsers().add(userB);
        distances.remove(index1);
        users.remove(index1);
        userList.add(userB);

        if (users.isEmpty()){
            add(cluster);
            cluster.calculateCluster();
            edit(cluster, cluster.getId());
            return userList;
        }

        int index2 = distances.indexOf(Collections.min(distances));
        userC = users.get(index2);
        userC.setCluster(cluster);
        cluster.getUsers().add(userC);
        userList.add(userC);

        add(cluster);
        cluster.calculateCluster();
        edit(cluster, cluster.getId());
        return userList;
    }

    @CrossOrigin
    @GetMapping("/clusters")
    public List<Cluster> clusters(){
        return clusterRepository.findAll();
    }

    @CrossOrigin
    @GetMapping("/clusters/create")
    public void create(){
        List<User> users = userController.users();
        users.removeIf(User::isNewUser);
        List<User> userList = new ArrayList<>(users);
        for (User user : users) {
            if (userList.isEmpty()){
                break;
            }
            else if (user.getCluster() == null){
                List<User> clusteredUsers = createCluster(user, userList);
                userList.removeAll(clusteredUsers);
            }
        }
    }

    @CrossOrigin
    @GetMapping("/clusters/all")
    public void getUserIdByCluster(){
        List<Cluster> clusters = clusterRepository.findAll();
        for (Cluster cluster : clusters){
            List<User> users = cluster.getUsers();
            for (User user : users){
                System.out.println(user.getId());
            }
        }
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
    @DeleteMapping("/clusters")
    public void delete ()
    {
        List<Cluster> clusters = clusterRepository.findAll();
        List<Cluster> clusterList = new ArrayList<>(clusters);
        for (Cluster cluster : clusterList){
            List<User> users = userRepository.getUsersByClusterId(cluster.getId());
            for (User user : users){
                user.setCluster(null);
            }
            clusterRepository.delete(cluster);
        }
    }

}
