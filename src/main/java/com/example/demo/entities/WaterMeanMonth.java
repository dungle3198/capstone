package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "water_mean_month")
public class WaterMeanMonth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column (name = "water_mm1")
    private double waterMeanMonth1;

    @Column (name = "water_mm2")
    private double waterMeanMonth2;

    @Column (name = "water_mm3")
    private double waterMeanMonth3;

    @Column (name = "water_mm4")
    private double waterMeanMonth4;

    @Column (name = "water_mm5")
    private double waterMeanMonth5;

    @Column (name = "water_mm6")
    private double waterMeanMonth6;

    @Column (name = "water_mm7")
    private double waterMeanMonth7;

    @Column (name = "water_mm8")
    private double waterMeanMonth8;

    @Column (name = "water_mm9")
    private double waterMeanMonth9;

    @Column (name = "water_mm10")
    private double waterMeanMonth10;

    @Column (name = "water_mm11")
    private double waterMeanMonth11;

    @Column (name = "water_mm12")
    private double waterMeanMonth12;

    @OneToOne(optional = false)
    @JoinColumn (name = "user_id", nullable = false)
    private User user;

    public WaterMeanMonth (){

    }

    public WaterMeanMonth(int id) {
        this.waterMeanMonth1 = 0;
        this.waterMeanMonth2 = 0;
        this.waterMeanMonth3 = 0;
        this.waterMeanMonth4 = 0;
        this.waterMeanMonth5 = 0;
        this.waterMeanMonth6 = 0;
        this.waterMeanMonth7 = 0;
        this.waterMeanMonth8 = 0;
        this.waterMeanMonth9 = 0;
        this.waterMeanMonth10 = 0;
        this.waterMeanMonth11 = 0;
        this.waterMeanMonth12 = 0;
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
                setWaterMeanMonth1(mean);
                break;
            case 2:
                setWaterMeanMonth2(mean);
                break;
            case 3:
                setWaterMeanMonth3(mean);
                break;
            case 4:
                setWaterMeanMonth4(mean);
                break;
            case 5:
                setWaterMeanMonth5(mean);
                break;
            case 6:
                setWaterMeanMonth6(mean);
                break;
            case 7:
                setWaterMeanMonth7(mean);
                break;
            case 8:
                setWaterMeanMonth8(mean);
                break;
            case 9:
                setWaterMeanMonth9(mean);
                break;
            case 10:
                setWaterMeanMonth10(mean);
                break;
            case 11:
                setWaterMeanMonth11(mean);
                break;
            case 12:
                setWaterMeanMonth12(mean);
                break;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getWaterMeanMonth1() {
        return waterMeanMonth1;
    }

    public void setWaterMeanMonth1(double waterMeanMonth1) {
        this.waterMeanMonth1 = waterMeanMonth1;
    }

    public double getWaterMeanMonth2() {
        return waterMeanMonth2;
    }

    public void setWaterMeanMonth2(double waterMeanMonth2) {
        this.waterMeanMonth2 = waterMeanMonth2;
    }

    public double getWaterMeanMonth3() {
        return waterMeanMonth3;
    }

    public void setWaterMeanMonth3(double waterMeanMonth3) {
        this.waterMeanMonth3 = waterMeanMonth3;
    }

    public double getWaterMeanMonth4() {
        return waterMeanMonth4;
    }

    public void setWaterMeanMonth4(double waterMeanMonth4) {
        this.waterMeanMonth4 = waterMeanMonth4;
    }

    public double getWaterMeanMonth5() {
        return waterMeanMonth5;
    }

    public void setWaterMeanMonth5(double waterMeanMonth5) {
        this.waterMeanMonth5 = waterMeanMonth5;
    }

    public double getWaterMeanMonth6() {
        return waterMeanMonth6;
    }

    public void setWaterMeanMonth6(double waterMeanMonth6) {
        this.waterMeanMonth6 = waterMeanMonth6;
    }

    public double getWaterMeanMonth7() {
        return waterMeanMonth7;
    }

    public void setWaterMeanMonth7(double waterMeanMonth7) {
        this.waterMeanMonth7 = waterMeanMonth7;
    }

    public double getWaterMeanMonth8() {
        return waterMeanMonth8;
    }

    public void setWaterMeanMonth8(double waterMeanMonth8) {
        this.waterMeanMonth8 = waterMeanMonth8;
    }

    public double getWaterMeanMonth9() {
        return waterMeanMonth9;
    }

    public void setWaterMeanMonth9(double waterMeanMonth9) {
        this.waterMeanMonth9 = waterMeanMonth9;
    }

    public double getWaterMeanMonth10() {
        return waterMeanMonth10;
    }

    public void setWaterMeanMonth10(double waterMeanMonth10) {
        this.waterMeanMonth10 = waterMeanMonth10;
    }

    public double getWaterMeanMonth11() {
        return waterMeanMonth11;
    }

    public void setWaterMeanMonth11(double waterMeanMonth11) {
        this.waterMeanMonth11 = waterMeanMonth11;
    }

    public double getWaterMeanMonth12() {
        return waterMeanMonth12;
    }

    public void setWaterMeanMonth12(double waterMeanMonth12) {
        this.waterMeanMonth12 = waterMeanMonth12;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Double> getWaterMeanMonthList(){
        List<Double> list = new ArrayList<>();
        list.add(waterMeanMonth1);
        list.add(waterMeanMonth2);
        list.add(waterMeanMonth3);
        list.add(waterMeanMonth4);
        list.add(waterMeanMonth5);
        list.add(waterMeanMonth6);
        list.add(waterMeanMonth7);
        list.add(waterMeanMonth8);
        list.add(waterMeanMonth9);
        list.add(waterMeanMonth10);
        list.add(waterMeanMonth11);
        list.add(waterMeanMonth12);
        return list;
    }
}
