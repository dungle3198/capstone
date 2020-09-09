package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import javax.persistence.*;
import java.util.List;

@Entity
@Table (name = "electricity_mean_month")
public class ElectricityMeanMonth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column (name = "electricity_mm1")
    private double electricityMeanMonth1;

    @Column (name = "electricity_mm2")
    private double electricityMeanMonth2;

    @Column (name = "electricity_mm3")
    private double electricityMeanMonth3;

    @Column (name = "electricity_mm4")
    private double electricityMeanMonth4;

    @Column (name = "electricity_mm5")
    private double electricityMeanMonth5;

    @Column (name = "electricity_mm6")
    private double electricityMeanMonth6;

    @Column (name = "electricity_mm7")
    private double electricityMeanMonth7;

    @Column (name = "electricity_mm8")
    private double electricityMeanMonth8;

    @Column (name = "electricity_mm9")
    private double electricityMeanMonth9;

    @Column (name = "electricity_mm10")
    private double electricityMeanMonth10;

    @Column (name = "electricity_mm11")
    private double electricityMeanMonth11;

    @Column (name = "electricity_mm12")
    private double electricityMeanMonth12;

    @OneToOne (optional = false)
    @JoinColumn (name = "user_id", nullable = false)
    private User user;

    public ElectricityMeanMonth(){

    }

    public ElectricityMeanMonth(int id) {
        this.electricityMeanMonth1 = 0;
        this.electricityMeanMonth2 = 0;
        this.electricityMeanMonth3 = 0;
        this.electricityMeanMonth4 = 0;
        this.electricityMeanMonth5 = 0;
        this.electricityMeanMonth6 = 0;
        this.electricityMeanMonth7 = 0;
        this.electricityMeanMonth8 = 0;
        this.electricityMeanMonth9 = 0;
        this.electricityMeanMonth10 = 0;
        this.electricityMeanMonth11 = 0;
        this.electricityMeanMonth12 = 0;
    }

    public void calculateElectricityMeanMonth (List<Double> amounts, int month){
        DescriptiveStatistics stats = new DescriptiveStatistics();

        // Add the data from the array
        for (Double amount : amounts) {
            stats.addValue(amount);
        }
        //mean
        double mean = stats.getMean();
        switch (month) {
            case 1:
                setElectricityMeanMonth1(mean);
                break;
            case 2:
                setElectricityMeanMonth2(mean);
                break;
            case 3:
                setElectricityMeanMonth3(mean);
                break;
            case 4:
                setElectricityMeanMonth4(mean);
                break;
            case 5:
                setElectricityMeanMonth5(mean);
                break;
            case 6:
                setElectricityMeanMonth6(mean);
                break;
            case 7:
                setElectricityMeanMonth7(mean);
                break;
            case 8:
                setElectricityMeanMonth8(mean);
                break;
            case 9:
                setElectricityMeanMonth9(mean);
                break;
            case 10:
                setElectricityMeanMonth10(mean);
                break;
            case 11:
                setElectricityMeanMonth11(mean);
                break;
            case 12:
                setElectricityMeanMonth12(mean);
                break;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getElectricityMeanMonth1() {
        return electricityMeanMonth1;
    }

    public void setElectricityMeanMonth1(double electricityMeanMonth1) {
        this.electricityMeanMonth1 = electricityMeanMonth1;
    }

    public double getElectricityMeanMonth2() {
        return electricityMeanMonth2;
    }

    public void setElectricityMeanMonth2(double electricityMeanMonth2) {
        this.electricityMeanMonth2 = electricityMeanMonth2;
    }

    public double getElectricityMeanMonth3() {
        return electricityMeanMonth3;
    }

    public void setElectricityMeanMonth3(double electricityMeanMonth3) {
        this.electricityMeanMonth3 = electricityMeanMonth3;
    }

    public double getElectricityMeanMonth4() {
        return electricityMeanMonth4;
    }

    public void setElectricityMeanMonth4(double electricityMeanMonth4) {
        this.electricityMeanMonth4 = electricityMeanMonth4;
    }

    public double getElectricityMeanMonth5() {
        return electricityMeanMonth5;
    }

    public void setElectricityMeanMonth5(double electricityMeanMonth5) {
        this.electricityMeanMonth5 = electricityMeanMonth5;
    }

    public double getElectricityMeanMonth6() {
        return electricityMeanMonth6;
    }

    public void setElectricityMeanMonth6(double electricityMeanMonth6) {
        this.electricityMeanMonth6 = electricityMeanMonth6;
    }

    public double getElectricityMeanMonth7() {
        return electricityMeanMonth7;
    }

    public void setElectricityMeanMonth7(double electricityMeanMonth7) {
        this.electricityMeanMonth7 = electricityMeanMonth7;
    }

    public double getElectricityMeanMonth8() {
        return electricityMeanMonth8;
    }

    public void setElectricityMeanMonth8(double electricityMeanMonth8) {
        this.electricityMeanMonth8 = electricityMeanMonth8;
    }

    public double getElectricityMeanMonth9() {
        return electricityMeanMonth9;
    }

    public void setElectricityMeanMonth9(double electricityMeanMonth9) {
        this.electricityMeanMonth9 = electricityMeanMonth9;
    }

    public double getElectricityMeanMonth10() {
        return electricityMeanMonth10;
    }

    public void setElectricityMeanMonth10(double electricityMeanMonth10) {
        this.electricityMeanMonth10 = electricityMeanMonth10;
    }

    public double getElectricityMeanMonth11() {
        return electricityMeanMonth11;
    }

    public void setElectricityMeanMonth11(double electricityMeanMonth11) {
        this.electricityMeanMonth11 = electricityMeanMonth11;
    }

    public double getElectricityMeanMonth12() {
        return electricityMeanMonth12;
    }

    public void setElectricityMeanMonth12(double electricityMeanMonth12) {
        this.electricityMeanMonth12 = electricityMeanMonth12;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
    
