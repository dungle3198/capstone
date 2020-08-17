package com.example.demo.entities;
import javax.persistence.*;
import java.lang.Math;
@Entity
@Table(name = "user_std")
public class UserStd{
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

    public UserStd(int id) {
        this.id = id;
        this.electricity = 0;
        this.internet = 0;
        this.water = 0;
        this.gas = 0;
    }
    public UserStd()
    {

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

    public double calculateStd(double [] arrays, double mean ,String billType)
    {
        double result = 0;
        double sum = 0;
        int n = arrays.length - 1;
        for(double value : arrays)
        {
            sum += (value-mean) * (value - mean);
        }
        result = Math.sqrt(sum/n);
        switch (billType)
        {
            case "internet":
                setInternet(result);
                break;
            case "water":
                setWater(result);
                break;
            case "gas":
                setGas(result);
                break;
            case "electricity":
                setElectricity(result);
                break;
        }
        return result;
    }
}
