package com.example.demo.entities;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Entity
@Table(name = "cluster_mean")
public class ClusterMean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "electricity")
    private double electricity;

    @Column(name = "internet")
    private double internet;

    @Column(name = "water")
    private double water;

    @Column(name = "gas")
    private double gas;

    @Column(name = "electricitystd")
    private double electricitystd;

    @Column(name = "internetstd")
    private double internetstd;

    @Column(name = "waterstd")
    private double waterstd;

    @Column(name = "gasstd")
    private double gasstd;

    public ClusterMean()
    {

    }
    public ClusterMean(UserMean userMean, List<UserMean> listOfUserMeans) {
        getDistance(userMean,listOfUserMeans);
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getElectricity() {
        return electricity;
    }

    public void setElectricity(double electricity) {
        this.electricity = electricity;
    }

    public double getInternet() {
        return internet;
    }

    public void setInternet(double internet) {
        this.internet = internet;
    }

    public double getWater() {
        return water;
    }

    public void setWater(double water) {
        this.water = water;
    }

    public double getGas() {
        return gas;
    }

    public void setGas(double gas) {
        this.gas = gas;
    }

    public void setStd(String type,double amount)
    {
        switch (type.toLowerCase())
        {
            case "internet":
                internetstd = amount;
                break;
            case "water":
                waterstd = amount;
                break;
            case "gas":
                gasstd = amount;
                break;
            case "electricity":
                electricitystd = amount;
                break;
        }
    }
    public double calculateMean(double [] amounts, String type)
    {
        DescriptiveStatistics stats = new DescriptiveStatistics();

        // Add the data from the array
        for (Double amount : amounts) {
            stats.addValue(amount);
        }
        //mean
        double mean = stats.getMean();
        switch (type.toLowerCase())
        {
            case "internet":
                setInternet(mean);
                break;
            case "water":
                setWater(mean);
                break;
            case "gas":
                setGas(mean);
                break;
            case "electricity":
                setElectricity(mean);
                break;
        }
        return mean;
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
        calculateBill(selectedUserMeans);
    }

    void calculateBill(List<UserMean> userMeans)
    {
        DescriptiveStatistics electricityStat = new DescriptiveStatistics();
        DescriptiveStatistics waterStat = new DescriptiveStatistics();
        DescriptiveStatistics gasStat = new DescriptiveStatistics();
        DescriptiveStatistics internetStat = new DescriptiveStatistics();


        List<Bill> listOfBills = new ArrayList<>();
        for (UserMean userMean : userMeans) {
            listOfBills.addAll(userMean.getUser().getBills());
        }

        for (Bill bill : listOfBills) {
            for (int j = 0; j < userMeans.size(); j++) {
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
        double clusterMeanElectricity = electricityStat.getMean();
        double clusterMeanGas = gasStat.getMean();
        double clusterMeanWater = waterStat.getMean();
        double clusterMeanInternet = internetStat.getMean();


        setElectricity(clusterMeanElectricity);
        setGas(clusterMeanGas);
        setWater(clusterMeanWater);
        setInternet(clusterMeanInternet);

        setStd("electricity",electricityStat.getStandardDeviation());
        setStd("gas",gasStat.getStandardDeviation());
        setStd("internet",internetStat.getStandardDeviation());
        setStd("water",waterStat.getStandardDeviation());
    }
}
