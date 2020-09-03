package com.example.demo.entities;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Entity
@Table(name = "cluster")
public class Cluster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "electricity_cluster_mean")
    private double electricityClusterMean;

    @Column(name = "internet_cluster_mean")
    private double internetClusterMean;

    @Column(name = "water_cluster_mean")
    private double waterClusterMean;

    @Column(name = "gas_cluster_mean")
    private double gasClusterMean;

    @Column(name = "electricity_cluster_std")
    private double electricityClusterStd;

    @Column(name = "internet_cluster_std")
    private double internetClusterStd;

    @Column(name = "water_cluster_std")
    private double waterClusterStd;

    @Column(name = "gas_cluster_std")
    private double gasClusterStd;

    @OneToMany(mappedBy = "cluster", cascade = CascadeType.PERSIST)
    List<User> users = new ArrayList<>();

    public Cluster()
    {

    }

    public Cluster(int id) {
        this.electricityClusterMean = 0;
        this.internetClusterMean = 0;
        this.waterClusterMean = 0;
        this.gasClusterMean = 0;
        this.electricityClusterStd = 0;
        this.internetClusterStd = 0;
        this.waterClusterStd = 0;
        this.gasClusterStd = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getElectricityClusterMean() {
        return electricityClusterMean;
    }

    public void setElectricityClusterMean(double electricityClusterMean) {
        this.electricityClusterMean = electricityClusterMean;
    }

    public double getInternetClusterMean() {
        return internetClusterMean;
    }

    public void setInternetClusterMean(double internetClusterMean) {
        this.internetClusterMean = internetClusterMean;
    }

    public double getWaterClusterMean() {
        return waterClusterMean;
    }

    public void setWaterClusterMean(double waterClusterMean) {
        this.waterClusterMean = waterClusterMean;
    }

    public double getGasClusterMean() {
        return gasClusterMean;
    }

    public void setGasClusterMean(double gasClusterMean) {
        this.gasClusterMean = gasClusterMean;
    }

    public double getElectricityClusterStd() {
        return electricityClusterStd;
    }

    public void setElectricityClusterStd(double electricityClusterStd) {
        this.electricityClusterStd = electricityClusterStd;
    }

    public double getInternetClusterStd() {
        return internetClusterStd;
    }

    public void setInternetClusterStd(double internetClusterStd) {
        this.internetClusterStd = internetClusterStd;
    }

    public double getWaterClusterStd() {
        return waterClusterStd;
    }

    public void setWaterClusterStd(double waterClusterStd) {
        this.waterClusterStd = waterClusterStd;
    }

    public double getGasClusterStd() {
        return gasClusterStd;
    }

    public void setGasClusterStd(double gasClusterStd) {
        this.gasClusterStd = gasClusterStd;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void getDistance(UserMean userMeanA, List<UserMean> listOfUserMeans)
    {
        if(userMeanA == null)
        {
            return;
        }
        List<UserMean> selectedUserMeans = new ArrayList<>();
        selectedUserMeans.add(userMeanA);
        double shortestDist = 999999;
        UserMean userMeanB = null;
        UserMean userMeanC = null;
        for (UserMean userMean : listOfUserMeans) {
            if (userMean.getId() == userMeanA.getId()) {
                continue;
            }
            double electricityDist = Math.pow(userMeanA.getElectricity() - userMean.getElectricity(), 2);
            double waterDist = Math.pow(userMeanA.getWater() - userMean.getWater(), 2);
            double gasDist = Math.pow(userMeanA.getGas() - userMean.getGas(), 2);
            double internetDist = Math.pow(userMeanA.getInternet() - userMean.getInternet(), 2);
            double square = electricityDist + waterDist + gasDist + internetDist;
            double distance = Math.sqrt(square);

            if (shortestDist > distance) {
                if (userMeanB != null) {
                    userMeanC = userMeanB;
                }
                userMeanB = userMean;
                shortestDist = distance;
            }
        }

        if(userMeanB != null)
        {
            selectedUserMeans.add(userMeanB);
        }
        if(userMeanC != null)
        {
            selectedUserMeans.add(userMeanC);
        }
        calculateCluster();
    }

    void calculateCluster()
    {
        DescriptiveStatistics electricityStat = new DescriptiveStatistics();
        DescriptiveStatistics waterStat = new DescriptiveStatistics();
        DescriptiveStatistics gasStat = new DescriptiveStatistics();
        DescriptiveStatistics internetStat = new DescriptiveStatistics();

        for (User user: users) {
            for (Bill bill: user.getBills()) {
                switch (bill.getType().toLowerCase()) {
                    case "electricity":
                        electricityStat.addValue(bill.getAmount());
                        break;
                    case "gas":
                        gasStat.addValue(bill.getAmount());
                        break;
                    case "water":
                        waterStat.addValue(bill.getAmount());
                        break;
                    case "internet":
                        internetStat.addValue(bill.getAmount());
                        break;
                }
            }
        }
        setElectricityClusterMean(electricityStat.getMean());
        setInternetClusterMean(internetStat.getMean());
        setGasClusterMean(gasStat.getMean());
        setWaterClusterMean(waterStat.getMean());
        setElectricityClusterStd(electricityStat.getStandardDeviation());
        setInternetClusterStd(internetStat.getStandardDeviation());
        setGasClusterStd(gasStat.getStandardDeviation());
        setWaterClusterStd(waterStat.getStandardDeviation());
    }
}
