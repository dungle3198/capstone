package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import javax.persistence.*;
import java.util.List;

@Entity
@Table (name = "internet_mean_month")
public class InternetMeanMonth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column (name = "internet_mm1")
    private double internet_mm1;

    @Column (name = "internet_mm2")
    private double internet_mm2;

    @Column (name = "internet_mm3")
    private double internet_mm3;

    @Column (name = "internet_mm4")
    private double internet_mm4;

    @Column (name = "internet_mm5")
    private double internet_mm5;

    @Column (name = "internet_mm6")
    private double internet_mm6;

    @Column (name = "internet_mm7")
    private double internet_mm7;

    @Column (name = "internet_mm8")
    private double internet_mm8;

    @Column (name = "internet_mm9")
    private double internet_mm9;

    @Column (name = "internet_mm10")
    private double internet_mm10;

    @Column (name = "internet_mm11")
    private double internet_mm11;

    @Column (name = "internet_mm12")
    private double internet_mm12;

    @OneToOne(optional = false)
    @JoinColumn (name = "user_id", nullable = false)
    private User user;
    
    public InternetMeanMonth(){
        
    }

    public InternetMeanMonth(int id) {
        this.internet_mm1 = 0;
        this.internet_mm2 = 0;
        this.internet_mm3 = 0;
        this.internet_mm4 = 0;
        this.internet_mm5 = 0;
        this.internet_mm6 = 0;
        this.internet_mm7 = 0;
        this.internet_mm8 = 0;
        this.internet_mm9 = 0;
        this.internet_mm10 = 0;
        this.internet_mm11 = 0;
        this.internet_mm12 = 0;
    }

    public void calculateInternetMeanMonth (List<Double> amounts, int month){
        DescriptiveStatistics stats = new DescriptiveStatistics();

        // Add the data from the array
        for (Double amount : amounts) {
            stats.addValue(amount);
        }
        //mean
        double mean = stats.getMean();
        switch (month) {
            case 1:
                setInternet_mm1(mean);
                break;
            case 2:
                setInternet_mm2(mean);
                break;
            case 3:
                setInternet_mm3(mean);
                break;
            case 4:
                setInternet_mm4(mean);
                break;
            case 5:
                setInternet_mm5(mean);
                break;
            case 6:
                setInternet_mm6(mean);
                break;
            case 7:
                setInternet_mm7(mean);
                break;
            case 8:
                setInternet_mm8(mean);
                break;
            case 9:
                setInternet_mm9(mean);
                break;
            case 10:
                setInternet_mm10(mean);
                break;
            case 11:
                setInternet_mm11(mean);
                break;
            case 12:
                setInternet_mm12(mean);
                break;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getInternet_mm1() {
        return internet_mm1;
    }

    public void setInternet_mm1(double internet_mm1) {
        this.internet_mm1 = internet_mm1;
    }

    public double getInternet_mm2() {
        return internet_mm2;
    }

    public void setInternet_mm2(double internet_mm2) {
        this.internet_mm2 = internet_mm2;
    }

    public double getInternet_mm3() {
        return internet_mm3;
    }

    public void setInternet_mm3(double internet_mm3) {
        this.internet_mm3 = internet_mm3;
    }

    public double getInternet_mm4() {
        return internet_mm4;
    }

    public void setInternet_mm4(double internet_mm4) {
        this.internet_mm4 = internet_mm4;
    }

    public double getInternet_mm5() {
        return internet_mm5;
    }

    public void setInternet_mm5(double internet_mm5) {
        this.internet_mm5 = internet_mm5;
    }

    public double getInternet_mm6() {
        return internet_mm6;
    }

    public void setInternet_mm6(double internet_mm6) {
        this.internet_mm6 = internet_mm6;
    }

    public double getInternet_mm7() {
        return internet_mm7;
    }

    public void setInternet_mm7(double internet_mm7) {
        this.internet_mm7 = internet_mm7;
    }

    public double getInternet_mm8() {
        return internet_mm8;
    }

    public void setInternet_mm8(double internet_mm8) {
        this.internet_mm8 = internet_mm8;
    }

    public double getInternet_mm9() {
        return internet_mm9;
    }

    public void setInternet_mm9(double internet_mm9) {
        this.internet_mm9 = internet_mm9;
    }

    public double getInternet_mm10() {
        return internet_mm10;
    }

    public void setInternet_mm10(double internet_mm10) {
        this.internet_mm10 = internet_mm10;
    }

    public double getInternet_mm11() {
        return internet_mm11;
    }

    public void setInternet_mm11(double internet_mm11) {
        this.internet_mm11 = internet_mm11;
    }

    public double getInternet_mm12() {
        return internet_mm12;
    }

    public void setInternet_mm12(double internet_mm12) {
        this.internet_mm12 = internet_mm12;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
