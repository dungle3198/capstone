package com.example.demo.entities;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import javax.persistence.*;
import java.util.ArrayList;
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

    public double getDistance(UserMean userMean)
    {
        double electricityDistance = Math.pow(userMean.getElectricity() - getElectricityClusterMean(), 2);
        double waterDistance = Math.pow(userMean.getWater() - getWaterClusterMean(), 2);
        double gasDistance = Math.pow(userMean.getGas() - getGasClusterMean(), 2);
        double internetDistance = Math.pow(userMean.getInternet() - getInternetClusterMean(), 2);
        return Math.sqrt(electricityDistance + waterDistance + gasDistance + internetDistance);
    }

    public void calculateCluster()
    {
        List<Double> electricityMeans = new ArrayList<>();
        List<Double> internetMeans = new ArrayList<>();
        List<Double> waterMeans = new ArrayList<>();
        List<Double> gasMeans = new ArrayList<>();
        List<Double> electricityStds = new ArrayList<>();
        List<Double> internetStds = new ArrayList<>();
        List<Double> waterStds = new ArrayList<>();
        List<Double> gasStds = new ArrayList<>();

        for (User user: users){
            UserMean userMean = user.getUserMean();
            UserStd userStd = user.getUserStd();
            electricityMeans.add(userMean.getElectricity());
            internetMeans.add(userMean.getInternet());
            waterMeans.add(userMean.getWater());
            gasMeans.add(userMean.getGas());
            electricityStds.add(userStd.getElectricity());
            internetStds.add(userStd.getInternet());
            waterStds.add(userStd.getWater());
            gasStds.add(userStd.getGas());
        }

        System.out.println(electricityMeans);
        System.out.println(electricityMeans.stream().mapToDouble(val -> val).average().orElse(0.0));
        setElectricityClusterMean(electricityMeans.stream().mapToDouble(val -> val).average().orElse(0.0));
        setInternetClusterMean(internetMeans.stream().mapToDouble(val -> val).average().orElse(0.0));
        setWaterClusterMean(waterMeans.stream().mapToDouble(val -> val).average().orElse(0.0));
        setGasClusterMean(gasMeans.stream().mapToDouble(val -> val).average().orElse(0.0));
        setElectricityClusterStd(electricityStds.stream().mapToDouble(val -> val).average().orElse(0.0));
        setInternetClusterStd(internetStds.stream().mapToDouble(val -> val).average().orElse(0.0));
        setWaterClusterStd(waterStds.stream().mapToDouble(val -> val).average().orElse(0.0));
        setGasClusterStd(gasStds.stream().mapToDouble(val -> val).average().orElse(0.0));
        System.out.println(getElectricityClusterMean());
        System.out.println(getElectricityClusterStd());
        System.out.println(getGasClusterMean());
        System.out.println(getGasClusterStd());
        System.out.println(getWaterClusterMean());
        System.out.println(getWaterClusterStd());
        System.out.println(getInternetClusterMean());
        System.out.println(getInternetClusterStd());
    }
}
