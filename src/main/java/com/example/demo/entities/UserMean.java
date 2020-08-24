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

    @Column(name = "totalElectricity")
    private double totalElectricity;

    @Column(name = "totalInternet")
    private double totalInternet;

    @Column(name = "totalWater")
    private double totalWater;

    @Column(name = "totalyGas")
    private double totalGas;

    @Column(name = "amountElectricity")
    private double amountElectricity;

    @Column(name = "amountInternet")
    private double amountInternet;

    @Column(name = "amountWater")
    private double amountWater;

    @Column(name = "amountGas")
    private double amountGas;
    public UserMean()
    {

    }
    public UserMean(int id) {
        this.id = id;
        this.electricity = 0;
        this.internet = 0;
        this.water = 0;
        this.gas = 0;
        this.amountElectricity = 0;
        this.amountInternet = 0;
        this.amountGas = 0;
        this.amountWater = 0;
        this.totalInternet =0;
        this.totalElectricity =0;
        this.totalGas = 0;
        this.totalWater = 0;
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
    public void addBill(Bill bill) {
        double Mean = 0;
        switch (bill.getType())
        {
            case "internet":
                totalInternet += bill.getAmount();
                amountInternet ++;
                setElectricity(totalInternet/amountInternet);
                break;
            case "water":
                totalWater += bill.getAmount();
                amountWater ++;
                setElectricity(totalWater/amountWater);
                break;
            case "gas":
                totalGas += bill.getAmount();
                amountGas ++;
                setElectricity(totalGas/amountGas);
                break;
            case "electricity":
                totalElectricity += bill.getAmount();
                amountElectricity ++;
                setElectricity(totalElectricity/amountElectricity);
                break;
        }
    }

}
