package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import javax.persistence.*;
import java.util.List;

@Entity
@Table (name = "water_mean_month")
public class WaterMeanMonth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column (name = "water_mm1")
    private double water_mm1;

    @Column (name = "water_mm2")
    private double water_mm2;

    @Column (name = "water_mm3")
    private double water_mm3;

    @Column (name = "water_mm4")
    private double water_mm4;

    @Column (name = "water_mm5")
    private double water_mm5;

    @Column (name = "water_mm6")
    private double water_mm6;

    @Column (name = "water_mm7")
    private double water_mm7;

    @Column (name = "water_mm8")
    private double water_mm8;

    @Column (name = "water_mm9")
    private double water_mm9;

    @Column (name = "water_mm10")
    private double water_mm10;

    @Column (name = "water_mm11")
    private double water_mm11;

    @Column (name = "water_mm12")
    private double water_mm12;

    @OneToOne(optional = false)
    @JoinColumn (name = "user_id", nullable = false)
    private User user;

    public WaterMeanMonth (){

    }

    public WaterMeanMonth(int id) {
        this.water_mm1 = 0;
        this.water_mm2 = 0;
        this.water_mm3 = 0;
        this.water_mm4 = 0;
        this.water_mm5 = 0;
        this.water_mm6 = 0;
        this.water_mm7 = 0;
        this.water_mm8 = 0;
        this.water_mm9 = 0;
        this.water_mm10 = 0;
        this.water_mm11 = 0;
        this.water_mm12 = 0;
    }

    public void calculateWaterMeanMonth (List<Double> amounts, int month){
        DescriptiveStatistics stats = new DescriptiveStatistics();

        // Add the data from the array
        for (Double amount : amounts) {
            stats.addValue(amount);
        }
        //mean
        double mean = stats.getMean();
        switch (month) {
            case 1:
                setWater_mm1(mean);
                break;
            case 2:
                setWater_mm2(mean);
                break;
            case 3:
                setWater_mm3(mean);
                break;
            case 4:
                setWater_mm4(mean);
                break;
            case 5:
                setWater_mm5(mean);
                break;
            case 6:
                setWater_mm6(mean);
                break;
            case 7:
                setWater_mm7(mean);
                break;
            case 8:
                setWater_mm8(mean);
                break;
            case 9:
                setWater_mm9(mean);
                break;
            case 10:
                setWater_mm10(mean);
                break;
            case 11:
                setWater_mm11(mean);
                break;
            case 12:
                setWater_mm12(mean);
                break;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getWater_mm1() {
        return water_mm1;
    }

    public void setWater_mm1(double water_mm1) {
        this.water_mm1 = water_mm1;
    }

    public double getWater_mm2() {
        return water_mm2;
    }

    public void setWater_mm2(double water_mm2) {
        this.water_mm2 = water_mm2;
    }

    public double getWater_mm3() {
        return water_mm3;
    }

    public void setWater_mm3(double water_mm3) {
        this.water_mm3 = water_mm3;
    }

    public double getWater_mm4() {
        return water_mm4;
    }

    public void setWater_mm4(double water_mm4) {
        this.water_mm4 = water_mm4;
    }

    public double getWater_mm5() {
        return water_mm5;
    }

    public void setWater_mm5(double water_mm5) {
        this.water_mm5 = water_mm5;
    }

    public double getWater_mm6() {
        return water_mm6;
    }

    public void setWater_mm6(double water_mm6) {
        this.water_mm6 = water_mm6;
    }

    public double getWater_mm7() {
        return water_mm7;
    }

    public void setWater_mm7(double water_mm7) {
        this.water_mm7 = water_mm7;
    }

    public double getWater_mm8() {
        return water_mm8;
    }

    public void setWater_mm8(double water_mm8) {
        this.water_mm8 = water_mm8;
    }

    public double getWater_mm9() {
        return water_mm9;
    }

    public void setWater_mm9(double water_mm9) {
        this.water_mm9 = water_mm9;
    }

    public double getWater_mm10() {
        return water_mm10;
    }

    public void setWater_mm10(double water_mm10) {
        this.water_mm10 = water_mm10;
    }

    public double getWater_mm11() {
        return water_mm11;
    }

    public void setWater_mm11(double water_mm11) {
        this.water_mm11 = water_mm11;
    }

    public double getWater_mm12() {
        return water_mm12;
    }

    public void setWater_mm12(double water_mm12) {
        this.water_mm12 = water_mm12;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
