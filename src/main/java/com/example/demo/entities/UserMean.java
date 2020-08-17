package com.example.demo.entities;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_mean")
public class UserMean {
    @Id
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
    public UserMean()
    {

    }
    public UserMean(int id) {
        this.id = id;
        this.electricity = 0;
        this.internet = 0;
        this.water = 0;
        this.gas = 0;
    }
    public int getId() {
        return id;
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
}
