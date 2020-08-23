package com.example.demo.entities;
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
    public ClusterMean()
    {

    }
    public ClusterMean(UserMean userMean, List<UserMean> listOfUserMeans, List<Bill> listOfBills) {
        getDistance(userMean,listOfUserMeans,listOfBills);
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

    public double calculateMean(double [] arrays, String billType)
    {
        double sum = 0;
        for(double value : arrays)
        {
            sum += value;
        }
        double mean = sum/ arrays.length;
        switch (billType)
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
    public void getDistance(UserMean usermeanA, List<UserMean> listOfUserMeans, List<Bill> listOfBills)
    {
        if(usermeanA == null)
        {
            System.out.println("the usermean does not exist");
            return;
        }
        List<UserMean> selectedUsers = new ArrayList<UserMean>();
        selectedUsers.add(usermeanA);
        double shortestDist = 999999;
        UserMean userMeanB = null;
        UserMean userMeanC = null;
        for (int i = 0; i < listOfUserMeans.size(); i++ ) {
            UserMean usermean = listOfUserMeans.get(i);
            if(usermean.getId() == usermeanA.getId())
            {
                continue;
            }
            double electricityDist = Math.pow(usermeanA.getElectricity() - usermean.getElectricity(),2);
            double waterDist = Math.pow(usermeanA.getWater() - usermean.getWater(),2);
            double gasDis = Math.pow(usermeanA.getGas() - usermean.getGas() ,2);
            double internetDis = Math.pow(usermeanA.getInternet() - usermean.getInternet(),2);
            double square = electricityDist + waterDist + gasDis + internetDis;
            double distance = Math.sqrt(square);

            if(shortestDist > distance)
            {
                if(userMeanB!= null)
                {
                    userMeanC= userMeanB;
                }
                userMeanB = usermean;
                shortestDist = distance;
            }
        }

        if(userMeanB != null)
        {
            selectedUsers.add(userMeanB);
        }
        if(userMeanC != null)
        {
            selectedUsers.add(userMeanC);
        }
        calculateBill(selectedUsers,listOfBills);
    }
    void calculateBill(List<UserMean> users,List<Bill> listOfBills)
    {
        double sum_electricity= 0;
        int count_electricity = 0;

        double sum_water= 0;
        int count_water = 0;

        double sum_gas= 0;
        int count_gas = 0;

        double sum_internet= 0;
        int count_internet = 0;
        for (int i =0; i < listOfBills.size();i++)
        {
            Bill bill = listOfBills.get(i);
            for(int j = 0; j < users.size(); j++)
            {
                UserMean user = users.get(j);
                if(bill.getUserId() == user.getId())
                {
                    if(bill.getType() == "electricity")
                    {
                        sum_electricity += bill.getAmount();
                        count_electricity++;
                    }
                    else if(bill.getType() == "gas")
                    {
                        sum_gas += bill.getAmount();
                        count_gas ++;
                    }
                    else if(bill.getType() == "water")
                    {
                        sum_water += bill.getAmount();
                        count_water ++;
                    }
                    else if(bill.getType() == "internet")
                    {
                        sum_internet += bill.getAmount();
                        count_internet ++;
                    }
                }

            }
        }
        double clusterMeanElectricity = sum_electricity/count_electricity;
        double clusterMeanGas = sum_gas/count_gas;
        double clusterMeanWater = sum_water/count_water;
        double clusterMeanInternet = sum_internet/count_internet;
        setElectricity(clusterMeanElectricity);
        setGas(clusterMeanGas);
        setWater(clusterMeanWater);
        setInternet(clusterMeanInternet);
    }
}
