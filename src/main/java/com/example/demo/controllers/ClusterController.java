package com.example.demo.controllers;

import java.util.*;
import java.util.stream.Collectors;

import com.example.demo.entities.*;
import com.example.demo.repositories.ClusterDetailRepository;
import com.example.demo.repositories.UserStatsRepository;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.repositories.ClusterRepository;

@RestController
public class ClusterController {

    private final ClusterRepository clusterRepository;
    private final UserController userController;
    private final UserStatsRepository userStatsRepository;
    private final ClusterDetailRepository clusterDetailRepository;

    @Autowired
    public ClusterController(ClusterRepository clusterRepository, UserController userController, UserStatsRepository userStatsRepository, ClusterDetailRepository clusterDetailRepository) {
        this.clusterRepository = clusterRepository;
        this.userController = userController;
        this.userStatsRepository = userStatsRepository;
        this.clusterDetailRepository = clusterDetailRepository;
    }

    public List<String> createUtilityList(User user){
        List<UserStats> userStatsList = userStatsRepository.getUserStatsByUserIdAndBillType(user.getId(), "Seasonal");
        List<String> utilityList = new ArrayList<>();
        if  (!userStatsList.isEmpty()) {
            for (UserStats userStats : userStatsList) {
                String categoryAndBiller = userStats.getCategory() + " by " + userStats.getBiller();
                utilityList.add(categoryAndBiller);
            }
        }
        return utilityList;
    }

    public void calculateClusterDetails(Cluster cluster, List<ClusterDetail> clusterDetailList){
        for (ClusterDetail clusterDetail : clusterDetailList){
            DescriptiveStatistics meanStats = new DescriptiveStatistics();
            DescriptiveStatistics standardDeviationStats = new DescriptiveStatistics();

            for (User user : cluster.getUsers()){
                List<UserStats> userStats = userStatsRepository.getUserStatsByUserIdAndCategoryAndBiller(
                        user.getId(), clusterDetail.getCategory(), clusterDetail.getBiller());
                if (!userStats.isEmpty()){
                    meanStats.addValue(userStats.get(0).getMean());
                    standardDeviationStats.addValue(userStats.get(0).getStandardDeviation());
                }
            }

            clusterDetail.setMean(meanStats.getMean());
            clusterDetail.setStandardDeviation(standardDeviationStats.getMean());
            clusterDetailRepository.save(clusterDetail);
        }
    }

    public void createAndCalculateClusterDetails(Cluster cluster, List<String> utilityList){
        List<ClusterDetail> clusterDetailList = new ArrayList<>();
        for (String utility : utilityList){
            ClusterDetail clusterDetail = new ClusterDetail();
            clusterDetail.setCluster(cluster);
            clusterDetail.setCategory(utility.split(" by ")[0]);
            clusterDetail.setBiller(utility.split(" by ")[1]);
            clusterDetailList.add(clusterDetail);
        }
        calculateClusterDetails(cluster, clusterDetailList);
    }

    public List<User> createCluster(User userA, List<User> users, int num){
        List<User> userList = new ArrayList<>();
        List<Double> distances = new ArrayList<>();
        List<String> utilityListA = createUtilityList(userA);
        Cluster cluster = new Cluster();
        users.remove(userA);

        userA.setCluster(cluster);
        cluster.getUsers().add(userA);
        userList.add(userA);
        if (users.isEmpty()){
            add(cluster);
            createAndCalculateClusterDetails(cluster, utilityListA);
            return userList;
        }

        for (User user : users) {
            double distance = Double.MAX_VALUE;
            List<String> commonUtility = new ArrayList<>(utilityListA);
            List<String> utilityList = createUtilityList(user);
            commonUtility.retainAll(utilityList);
            double sumOfDistance = 0;
            int numberOfPoints = 0;

            if (!commonUtility.isEmpty()) {
                for (String utility : commonUtility) {
                    List<UserStats> userStats = userStatsRepository.getUserStatsByUserIdAndCategoryAndBiller(
                            user.getId(), utility.split(" by ")[0], utility.split(" by ")[1]);
                    List<UserStats> userStatsA = userStatsRepository.getUserStatsByUserIdAndCategoryAndBiller(
                            userA.getId(), utility.split(" by ")[0], utility.split(" by ")[1]);
                    sumOfDistance += Math.pow(userStatsA.get(0).getMean() - userStats.get(0).getMean(), 2) +
                            Math.pow(userStatsA.get(0).getStandardDeviation() - userStats.get(0).getStandardDeviation(), 2);
                    numberOfPoints += 2;
                }

                if (sumOfDistance != 0) {
                    distance = (1 / Math.sqrt(numberOfPoints)) * Math.sqrt(sumOfDistance);
                    System.out.println(numberOfPoints);
                }
            }
            distances.add(distance);
            System.out.println(distance);
        }

        List<String> newUtilityList = new ArrayList<>(utilityListA);
        for (int i = 0; i < num - 1; i++) {
            User user;
            int index = distances.indexOf(Collections.min(distances));
            user = users.get(index);
            List<String> utilityList = createUtilityList(user);
            newUtilityList.addAll(utilityList);

            user.setCluster(cluster);
            cluster.getUsers().add(user);
            distances.remove(index);
            users.remove(index);
            userList.add(user);
            if (users.isEmpty()){
                break;
            }
        }

        List<String> result = newUtilityList.stream().distinct().collect(Collectors.toList());
        System.out.println("RESULT = " + result);
        add(cluster);
        createAndCalculateClusterDetails(cluster, result);
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
        List<User> users = userController.getUsersWithBills();
        //users.removeIf(User::isNewUser);
        List<User> userList = new ArrayList<>(users);
        for (User user : users){
            List<UserStats> userStatsList = userStatsRepository.getUserStatsByUserIdAndBillType(user.getId(), "Seasonal");
            if (userStatsList.isEmpty()){
                userList.remove(user);
            }
        }

        users.retainAll(userList);
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
    @GetMapping("/clusters/{id}")
    public Optional<Cluster> getClusterById(@PathVariable("id") final int id)
    {
        return clusterRepository.findById(id);
    }

    @CrossOrigin
    @PostMapping("/clusters")
    public void add(@RequestBody Cluster cluster)
    {
        clusterRepository.save(cluster);
    }

    @CrossOrigin
    @PutMapping("/clusters/{id}")
    public void edit(@RequestBody Cluster cluster, @PathVariable("id") final int id){
        if (getClusterById(id).isPresent()){
            clusterRepository.save(cluster);
        }
    }

    @CrossOrigin
    @DeleteMapping("/clusters")
    public void delete(){
        List<Cluster> clusters = clusters();
        List<Cluster> clusterList = new ArrayList<>(clusters);
        for (Cluster cluster : clusterList){
            List<User> users = userController.getUsersByClusterId(cluster.getId());
            for (User user : users){
                user.setCluster(null);
            }
            clusterRepository.delete(cluster);
        }
    }
}
