package com.example.demo.controllers;

import java.util.*;
import java.util.stream.Collectors;

import com.example.demo.entities.*;
import com.example.demo.repositories.ClusterStatsRepository;
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
    private final ClusterStatsRepository clusterStatsRepository;
    private final ClusterDetailRepository clusterDetailRepository;

    @Autowired
    public ClusterController(ClusterRepository clusterRepository, UserController userController, UserStatsRepository userStatsRepository, ClusterStatsRepository clusterStatsRepository, ClusterDetailRepository clusterDetailRepository) {
        this.clusterRepository = clusterRepository;
        this.userController = userController;
        this.userStatsRepository = userStatsRepository;
        this.clusterStatsRepository = clusterStatsRepository;
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

    //Calculate mean and standard of categories of the cluster
    public void calculateClusterStats(List<ClusterStats> clusterStatsList, List<User> users){
        for (ClusterStats clusterStats : clusterStatsList){
            DescriptiveStatistics meanStats = new DescriptiveStatistics();
            DescriptiveStatistics standardDeviationStats = new DescriptiveStatistics();

            for (User user : users){
                List<UserStats> userStats = userStatsRepository.getUserStatsByUserIdAndCategoryAndBiller(
                        user.getId(), clusterStats.getCategory(), clusterStats.getBiller());
                if (!userStats.isEmpty()){
                    meanStats.addValue(userStats.get(0).getMean());
                    standardDeviationStats.addValue(userStats.get(0).getStandardDeviation());
                }
            }

            clusterStats.setMean(meanStats.getMean());
            clusterStats.setStandardDeviation(standardDeviationStats.getMean());
            clusterStatsRepository.save(clusterStats);
        }
    }

    public void createAndCalculateClusterStats(Cluster cluster, List<String> utilityList, List<User> users){
        List<ClusterStats> clusterStatsList = new ArrayList<>();
        for (String utility : utilityList){
            ClusterStats clusterStats = new ClusterStats();
            clusterStats.setCluster(cluster);
            clusterStats.setCategory(utility.split(" by ")[0]);
            clusterStats.setBiller(utility.split(" by ")[1]);
            clusterStatsList.add(clusterStats);
        }
        calculateClusterStats(clusterStatsList, users);
    }

    //Create cluster, cluster stats and cluster details
    public void createCluster(User userA, List<User> users, int num){
        List<User> proxy = new ArrayList<>(users);
        List<User> userList = new ArrayList<>();
        List<Double> distances = new ArrayList<>();
        List<String> utilityListA = createUtilityList(userA);
        Cluster cluster = new Cluster();
        proxy.remove(userA);

        cluster.setUserId(userA.getId());
        userList.add(userA);

        for (User user : proxy) {
            double distance = Double.MAX_VALUE;
            List<String> commonUtilityList = new ArrayList<>(utilityListA);
            List<String> utilityList = createUtilityList(user);
            commonUtilityList.retainAll(utilityList);
            double sumOfDistance = 0;
            int numberOfPoints = 0;

            if (!commonUtilityList.isEmpty()) {
                for (String utility : commonUtilityList) {
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

        System.out.println(distances);
        List<String> newUtilityList = new ArrayList<>(utilityListA);
        for (int i = 0; i < num - 1; i++) {
            User user;
            int index = distances.indexOf(Collections.min(distances));
            System.out.println(index);
            user = proxy.get(index);
            List<String> utilityList = createUtilityList(user);
            newUtilityList.addAll(utilityList);
            distances.remove(index);
            proxy.remove(index);
            userList.add(user);
        }

        List<String> result = newUtilityList.stream().distinct().collect(Collectors.toList());
        System.out.println("RESULT = " + result);
        add(cluster);
        createAndCalculateClusterStats(cluster, result, userList);
        for (User user : userList){
            ClusterDetail clusterDetail = new ClusterDetail();
            clusterDetail.setUser(user);
            clusterDetail.setCluster(cluster);
            clusterDetailRepository.save(clusterDetail);
        }
    }

    @CrossOrigin
    @GetMapping("/clusters")
    public List<Cluster> clusters(){
        return clusterRepository.findAll();
    }

    //Create cluster
    @CrossOrigin
    @GetMapping("/clusters/create")
    public void create(){
        if (!clusters().isEmpty()){
            delete();
        }

        List<User> users = userController.getUsersWithBills();
        List<User> userList = new ArrayList<>(users);
        for (User user : users){
            List<UserStats> userStatsList = userStatsRepository.getUserStatsByUserIdAndBillType(
                                                    user.getId(), "Seasonal");
            if (userStatsList.isEmpty()){
                userList.remove(user);
            }
            else {
                for (UserStats userStats : userStatsList){
                    if (userStats.getNumberOfBills() < 5){
                        userList.remove(user);
                    }
                }
            }
        }

        users.retainAll(userList);
        for (User user : users) {
            createCluster(user, userList, 3);
        }
    }

    @CrossOrigin
    @GetMapping("/clusters/{id}")
    public Optional<Cluster> getClusterById(@PathVariable("id") final int id)
    {
        return clusterRepository.findById(id);
    }

    @CrossOrigin
    @GetMapping("/clusters/user/{id}")
    public List<Cluster> getClusterByPrimaryUserId(@PathVariable("id") final int id)
    {
        if (userController.getUserById(id).isPresent()) {
            return clusterRepository.getClusterByPrimaryUserId(id);
        }
        return null;
    }

    @CrossOrigin
    @PostMapping("/clusters")
    public void add(@RequestBody Cluster cluster)
    {
        if (userController.getUserById(cluster.getUserId()).isPresent()) {
            clusterRepository.save(cluster);
        }
    }

    @CrossOrigin
    @PutMapping("/clusters/{id}")
    public void edit(@RequestBody Cluster cluster, @PathVariable("id") final int id){
        Cluster existingCluster;
        if (userController.getUserById(cluster.getUserId()).isPresent() &&
                getClusterById(id).isPresent()){
            existingCluster = getClusterById(id).get();
        }
        else {return;}
        existingCluster.setId(id);
        existingCluster.setUserId(cluster.getUserId());
        clusterRepository.save(cluster);
    }

    @CrossOrigin
    @DeleteMapping("/clusters")
    public void delete(){
        List<Cluster> clusters = clusters();
        List<Cluster> clusterList = new ArrayList<>(clusters);
        for (Cluster cluster : clusterList){
            clusterRepository.delete(cluster);
        }
    }
}
