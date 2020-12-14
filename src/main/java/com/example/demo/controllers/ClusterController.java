package com.example.demo.controllers;

import java.util.*;

import com.example.demo.entities.*;
import com.example.demo.repositories.UserRepository;
import com.example.demo.repositories.UserStatsRepository;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.repositories.ClusterRepository;

@RestController
public class ClusterController {

    private final ClusterRepository clusterRepository;
    private final UserRepository userRepository;
    private final UserStatsRepository userStatsRepository;

    @Autowired
    public ClusterController(ClusterRepository clusterRepository, UserRepository userRepository, UserStatsRepository userStatsRepository) {
        this.clusterRepository = clusterRepository;
        this.userRepository = userRepository;
        this.userStatsRepository = userStatsRepository;
    }

    public void calculateCluster(Cluster cluster){
        List<String> listOfUtility = Arrays.asList("Electricity", "Phone and Internet", "Water", "Gas");
        Map<String, List<Double>> utilityStatsMap = new HashMap<>();

        for (String utility : listOfUtility){
            DescriptiveStatistics meanStats = new DescriptiveStatistics();
            DescriptiveStatistics stdStats = new DescriptiveStatistics();
            List<Double> statsList = new ArrayList<>();

            for (User user : cluster.getUsers()){
                List<UserStats> userStats = userStatsRepository.getUserStatsByUserIdAndCategoryAndBiller(
                        user.getId(), utility, "");
                if (!userStats.isEmpty()){
                    meanStats.addValue(userStats.get(0).getMean());
                    stdStats.addValue(userStats.get(0).getStandardDeviation());
                }
            }

            statsList.add(meanStats.getMean());
            statsList.add(stdStats.getMean());
            utilityStatsMap.put(utility, statsList);
            System.out.println(utilityStatsMap);
        }

        cluster.setElectricityClusterMean(utilityStatsMap.get("Electricity").get(0));
        cluster.setElectricityClusterStd(utilityStatsMap.get("Electricity").get(1));
        cluster.setInternetClusterMean(utilityStatsMap.get("Phone and Internet").get(0));
        cluster.setInternetClusterStd(utilityStatsMap.get("Phone and Internet").get(1));
        cluster.setWaterClusterMean(utilityStatsMap.get("Water").get(0));
        cluster.setWaterClusterStd(utilityStatsMap.get("Water").get(1));
        cluster.setGasClusterMean(utilityStatsMap.get("Gas").get(0));
        cluster.setGasClusterStd(utilityStatsMap.get("Gas").get(1));
    }

    public List<String> createUtilityList(User user){
        List<BillData> billDataList = user.getBillData();
        List<String> utilityList = new ArrayList<>();
        for (BillData billData : billDataList){
            if (!utilityList.contains(billData.getCategory())){
                utilityList.add(billData.getCategory());
            }
        }
        System.out.println(utilityList);
        return utilityList;
    }

    public List<User> createCluster(User userA, List<User> users, int num){
        List<User> userList = new ArrayList<>();
        List<Double> distances = new ArrayList<>();
        Cluster cluster = new Cluster();
        users.remove(userA);

        userA.setCluster(cluster);
        cluster.getUsers().add(userA);
        userList.add(userA);
        if (users.isEmpty()){
            add(cluster);
            calculateCluster(cluster);
            edit(cluster, cluster.getId());
            return userList;
        }

        for (User user : users) {
            double distance = Double.MAX_VALUE;
            List<String> listOfUtility = Arrays.asList("Electricity", "Phone and Internet", "Water", "Gas");
            List<String> utilityList = createUtilityList(user);
            List<String> utilityListA = createUtilityList(userA);
            utilityList.addAll(utilityListA);
            System.out.println(utilityList);
            double sumOfDistance = 0;
            int numberOfPoints = 0;

            for (String utility : listOfUtility){
                if (Collections.frequency(utilityList, utility) == 2){
                    List<UserStats> userStats = userStatsRepository.getUserStatsByUserIdAndCategoryAndBiller(
                            user.getId(), utility, "");
                    List<UserStats> userStatsA = userStatsRepository.getUserStatsByUserIdAndCategoryAndBiller(
                            userA.getId(), utility, "");
                    sumOfDistance += Math.pow(userStatsA.get(0).getMean() - userStats.get(0).getMean(), 2) +
                            Math.pow(userStatsA.get(0).getStandardDeviation() - userStats.get(0).getStandardDeviation(), 2);
                    numberOfPoints += 2;
                }
            }

            if (sumOfDistance != 0) {
                distance = (1 / Math.sqrt(numberOfPoints)) * Math.sqrt(sumOfDistance);
                System.out.println(numberOfPoints);
            }
            distances.add(distance);
            System.out.println(distance);
        }

        for (int i = 0; i < num - 1; i++) {
            User user;
            int index = distances.indexOf(Collections.min(distances));
            user = users.get(index);
            user.setCluster(cluster);
            cluster.getUsers().add(user);
            distances.remove(index);
            users.remove(index);
            userList.add(user);
            if (users.isEmpty()){
                break;
            }
        }

        add(cluster);
        calculateCluster(cluster);
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
        List<User> users = userRepository.getUsers();
        //users.removeIf(User::isNewUser);
        List<User> userList = new ArrayList<>(users);
        for (User user : users) {
            if (userList.isEmpty()){
                break;
            }
            else if (user.getCluster() == null){
                List<User> clusteredUsers = createCluster(user, userList, 3);
                userList.removeAll(clusteredUsers);
            }
        }
    }

    @CrossOrigin
    @GetMapping("/clusters/all")
    public void getUserIdByCluster() {
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
