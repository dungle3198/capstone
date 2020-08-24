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

    @Column(name = "total_electricity")
    private double totalElectricity;

    @Column(name = "total_internet")
    private double totalInternet;

    @Column(name = "total_water")
    private double totalWater;

    @Column(name = "total_gas")
    private double totalGas;

    @Column(name = "amount_electricity")
    private double amountElectricity;

    @Column(name = "amount_internet")
    private double amountInternet;

    @Column(name = "amount_water")
    private double amountWater;

    @Column(name = "amount_gas")
    private double amountGas;

    @OneToOne (optional = false)
    @JoinColumn (name = "user_id", nullable = false)
    private User user;


    public UserMean()
    {

    }

    public UserMean(int id) {
        this.electricity = 0;
        this.internet = 0;
        this.water = 0;
        this.gas = 0;
        this.totalInternet =0;
        this.totalElectricity =0;
        this.totalWater = 0;
        this.totalGas = 0;
        this.amountElectricity = 0;
        this.amountInternet = 0;
        this.amountWater = 0;
        this.amountGas = 0;
    }

    public double calculateMean(Bill bill) {
        double mean = 0;
        switch (bill.getType().toLowerCase())
        {
            case "internet":
                totalInternet += bill.getAmount();
                amountInternet ++;
                mean = totalInternet/amountInternet;
                setInternet(mean);
                break;
            case "water":
                totalWater += bill.getAmount();
                amountWater ++;
                mean = totalWater/amountWater;
                setWater(mean);
                break;
            case "gas":
                totalGas += bill.getAmount();
                amountGas ++;
                mean = totalGas/amountGas;
                setGas(mean);
                break;
            case "electricity":
                totalElectricity += bill.getAmount();
                amountElectricity ++;
                mean = totalElectricity/amountElectricity;
                setElectricity(mean);
                break;
        }
        return mean;
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

    public double getTotalElectricity() {
        return totalElectricity;
    }

    public void setTotalElectricity(double totalElectricity) {
        this.totalElectricity = totalElectricity;
    }

    public double getTotalInternet() {
        return totalInternet;
    }

    public void setTotalInternet(double totalInternet) {
        this.totalInternet = totalInternet;
    }

    public double getTotalWater() {
        return totalWater;
    }

    public void setTotalWater(double totalWater) {
        this.totalWater = totalWater;
    }

    public double getTotalGas() {
        return totalGas;
    }

    public void setTotalGas(double totalGas) {
        this.totalGas = totalGas;
    }

    public double getAmountElectricity() {
        return amountElectricity;
    }

    public void setAmountElectricity(double amountElectricity) {
        this.amountElectricity = amountElectricity;
    }

    public double getAmountInternet() {
        return amountInternet;
    }

    public void setAmountInternet(double amountInternet) {
        this.amountInternet = amountInternet;
    }

    public double getAmountWater() {
        return amountWater;
    }

    public void setAmountWater(double amountWater) {
        this.amountWater = amountWater;
    }

    public double getAmountGas() {
        return amountGas;
    }

    public void setAmountGas(double amountGas) {
        this.amountGas = amountGas;
    }

}
