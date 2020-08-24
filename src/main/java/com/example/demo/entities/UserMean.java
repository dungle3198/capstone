package com.example.demo.entities;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user_mean")
public class UserMean {
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

    public UserMean()
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

    public double calculateMean(List<Double> arrays, String billType)
    {
        double sum = 0;
        for(double value : arrays)
        {
            sum += value;
        }
        double mean = sum/ arrays.size();
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
