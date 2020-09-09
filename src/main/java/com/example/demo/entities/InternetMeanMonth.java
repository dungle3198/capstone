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
    private double internetMeanMonth1;

    @Column (name = "internet_mm2")
    private double internetMeanMonth2;

    @Column (name = "internet_mm3")
    private double internetMeanMonth3;

    @Column (name = "internet_mm4")
    private double internetMeanMonth4;

    @Column (name = "internet_mm5")
    private double internetMeanMonth5;

    @Column (name = "internet_mm6")
    private double internetMeanMonth6;

    @Column (name = "internet_mm7")
    private double internetMeanMonth7;

    @Column (name = "internet_mm8")
    private double internetMeanMonth8;

    @Column (name = "internet_mm9")
    private double internetMeanMonth9;

    @Column (name = "internet_mm10")
    private double internetMeanMonth10;

    @Column (name = "internet_mm11")
    private double internetMeanMonth11;

    @Column (name = "internet_mm12")
    private double internetMeanMonth12;

    @OneToOne(optional = false)
    @JoinColumn (name = "user_id", nullable = false)
    private User user;
    
    public InternetMeanMonth(){
        
    }

    public InternetMeanMonth(int id) {
        this.internetMeanMonth1 = 0;
        this.internetMeanMonth2 = 0;
        this.internetMeanMonth3 = 0;
        this.internetMeanMonth4 = 0;
        this.internetMeanMonth5 = 0;
        this.internetMeanMonth6 = 0;
        this.internetMeanMonth7 = 0;
        this.internetMeanMonth8 = 0;
        this.internetMeanMonth9 = 0;
        this.internetMeanMonth10 = 0;
        this.internetMeanMonth11 = 0;
        this.internetMeanMonth12 = 0;
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
                setInternetMeanMonth1(mean);
                break;
            case 2:
                setInternetMeanMonth2(mean);
                break;
            case 3:
                setInternetMeanMonth3(mean);
                break;
            case 4:
                setInternetMeanMonth4(mean);
                break;
            case 5:
                setInternetMeanMonth5(mean);
                break;
            case 6:
                setInternetMeanMonth6(mean);
                break;
            case 7:
                setInternetMeanMonth7(mean);
                break;
            case 8:
                setInternetMeanMonth8(mean);
                break;
            case 9:
                setInternetMeanMonth9(mean);
                break;
            case 10:
                setInternetMeanMonth10(mean);
                break;
            case 11:
                setInternetMeanMonth11(mean);
                break;
            case 12:
                setInternetMeanMonth12(mean);
                break;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getInternetMeanMonth1() {
        return internetMeanMonth1;
    }

    public void setInternetMeanMonth1(double internetMeanMonth1) {
        this.internetMeanMonth1 = internetMeanMonth1;
    }

    public double getInternetMeanMonth2() {
        return internetMeanMonth2;
    }

    public void setInternetMeanMonth2(double internetMeanMonth2) {
        this.internetMeanMonth2 = internetMeanMonth2;
    }

    public double getInternetMeanMonth3() {
        return internetMeanMonth3;
    }

    public void setInternetMeanMonth3(double internetMeanMonth3) {
        this.internetMeanMonth3 = internetMeanMonth3;
    }

    public double getInternetMeanMonth4() {
        return internetMeanMonth4;
    }

    public void setInternetMeanMonth4(double internetMeanMonth4) {
        this.internetMeanMonth4 = internetMeanMonth4;
    }

    public double getInternetMeanMonth5() {
        return internetMeanMonth5;
    }

    public void setInternetMeanMonth5(double internetMeanMonth5) {
        this.internetMeanMonth5 = internetMeanMonth5;
    }

    public double getInternetMeanMonth6() {
        return internetMeanMonth6;
    }

    public void setInternetMeanMonth6(double internetMeanMonth6) {
        this.internetMeanMonth6 = internetMeanMonth6;
    }

    public double getInternetMeanMonth7() {
        return internetMeanMonth7;
    }

    public void setInternetMeanMonth7(double internetMeanMonth7) {
        this.internetMeanMonth7 = internetMeanMonth7;
    }

    public double getInternetMeanMonth8() {
        return internetMeanMonth8;
    }

    public void setInternetMeanMonth8(double internetMeanMonth8) {
        this.internetMeanMonth8 = internetMeanMonth8;
    }

    public double getInternetMeanMonth9() {
        return internetMeanMonth9;
    }

    public void setInternetMeanMonth9(double internetMeanMonth9) {
        this.internetMeanMonth9 = internetMeanMonth9;
    }

    public double getInternetMeanMonth10() {
        return internetMeanMonth10;
    }

    public void setInternetMeanMonth10(double internetMeanMonth10) {
        this.internetMeanMonth10 = internetMeanMonth10;
    }

    public double getInternetMeanMonth11() {
        return internetMeanMonth11;
    }

    public void setInternetMeanMonth11(double internetMeanMonth11) {
        this.internetMeanMonth11 = internetMeanMonth11;
    }

    public double getInternetMeanMonth12() {
        return internetMeanMonth12;
    }

    public void setInternetMeanMonth12(double internetMeanMonth12) {
        this.internetMeanMonth12 = internetMeanMonth12;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
