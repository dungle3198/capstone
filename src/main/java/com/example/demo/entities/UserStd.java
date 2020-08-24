package com.example.demo.entities;
import com.fasterxml.jackson.annotation.JacksonInject;

import javax.persistence.*;
import java.lang.Math;
import java.util.List;

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

    @OneToOne (optional = false)
    @JoinColumn (name = "user_id", nullable = false)
    private User user;

    public UserStd()
    {

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getElectricity() {
        return electricity;
    }

    public void setElectricity(double electricity) {
        this.electricity = 0;
    }

    public double getInternet() {
        return internet;
    }

    public void setInternet(double internet) {
        this.internet = 0;
    }

    public double getWater() {
        return water;
    }

    public void setWater(double water) {
        this.water = 0;
    }

    public double getGas() {
        return gas;
    }

    public void setGas(double gas) {
        this.gas = 0;
    }

    public double calculateStd(List<Double> arrays, double mean , String billType)
    {
        double result = 0;
        double sum = 0;
        int n = arrays.size() - 1;
        for(double value : arrays)
        {
            sum += (value-mean) * (value - mean);
        }
        result = Math.sqrt(sum/(n-1));
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
