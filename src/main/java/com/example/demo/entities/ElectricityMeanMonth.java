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
    private double electricity_mm1;

    @Column (name = "electricity_mm2")
    private double electricity_mm2;

    @Column (name = "electricity_mm3")
    private double electricity_mm3;

    @Column (name = "electricity_mm4")
    private double electricity_mm4;

    @Column (name = "electricity_mm5")
    private double electricity_mm5;

    @Column (name = "electricity_mm6")
    private double electricity_mm6;

    @Column (name = "electricity_mm7")
    private double electricity_mm7;

    @Column (name = "electricity_mm8")
    private double electricity_mm8;

    @Column (name = "electricity_mm9")
    private double electricity_mm9;

    @Column (name = "electricity_mm10")
    private double electricity_mm10;

    @Column (name = "electricity_mm11")
    private double electricity_mm11;

    @Column (name = "electricity_mm12")
    private double electricity_mm12;

    @OneToOne (optional = false)
    @JoinColumn (name = "user_id", nullable = false)
    private User user;

    public ElectricityMeanMonth(){

    }

    public ElectricityMeanMonth(int id) {
        this.electricity_mm1 = 0;
        this.electricity_mm2 = 0;
        this.electricity_mm3 = 0;
        this.electricity_mm4 = 0;
        this.electricity_mm5 = 0;
        this.electricity_mm6 = 0;
        this.electricity_mm7 = 0;
        this.electricity_mm8 = 0;
        this.electricity_mm9 = 0;
        this.electricity_mm10 = 0;
        this.electricity_mm11 = 0;
        this.electricity_mm12 = 0;
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
                setElectricity_mm1(mean);
                break;
            case 2:
                setElectricity_mm2(mean);
                break;
            case 3:
                setElectricity_mm3(mean);
                break;
            case 4:
                setElectricity_mm4(mean);
                break;
            case 5:
                setElectricity_mm5(mean);
                break;
            case 6:
                setElectricity_mm6(mean);
                break;
            case 7:
                setElectricity_mm7(mean);
                break;
            case 8:
                setElectricity_mm8(mean);
                break;
            case 9:
                setElectricity_mm9(mean);
                break;
            case 10:
                setElectricity_mm10(mean);
                break;
            case 11:
                setElectricity_mm11(mean);
                break;
            case 12:
                setElectricity_mm12(mean);
                break;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getElectricity_mm1() {
        return electricity_mm1;
    }

    public void setElectricity_mm1(double electricity_mm1) {
        this.electricity_mm1 = electricity_mm1;
    }

    public double getElectricity_mm2() {
        return electricity_mm2;
    }

    public void setElectricity_mm2(double electricity_mm2) {
        this.electricity_mm2 = electricity_mm2;
    }

    public double getElectricity_mm3() {
        return electricity_mm3;
    }

    public void setElectricity_mm3(double electricity_mm3) {
        this.electricity_mm3 = electricity_mm3;
    }

    public double getElectricity_mm4() {
        return electricity_mm4;
    }

    public void setElectricity_mm4(double electricity_mm4) {
        this.electricity_mm4 = electricity_mm4;
    }

    public double getElectricity_mm5() {
        return electricity_mm5;
    }

    public void setElectricity_mm5(double electricity_mm5) {
        this.electricity_mm5 = electricity_mm5;
    }

    public double getElectricity_mm6() {
        return electricity_mm6;
    }

    public void setElectricity_mm6(double electricity_mm6) {
        this.electricity_mm6 = electricity_mm6;
    }

    public double getElectricity_mm7() {
        return electricity_mm7;
    }

    public void setElectricity_mm7(double electricity_mm7) {
        this.electricity_mm7 = electricity_mm7;
    }

    public double getElectricity_mm8() {
        return electricity_mm8;
    }

    public void setElectricity_mm8(double electricity_mm8) {
        this.electricity_mm8 = electricity_mm8;
    }

    public double getElectricity_mm9() {
        return electricity_mm9;
    }

    public void setElectricity_mm9(double electricity_mm9) {
        this.electricity_mm9 = electricity_mm9;
    }

    public double getElectricity_mm10() {
        return electricity_mm10;
    }

    public void setElectricity_mm10(double electricity_mm10) {
        this.electricity_mm10 = electricity_mm10;
    }

    public double getElectricity_mm11() {
        return electricity_mm11;
    }

    public void setElectricity_mm11(double electricity_mm11) {
        this.electricity_mm11 = electricity_mm11;
    }

    public double getElectricity_mm12() {
        return electricity_mm12;
    }

    public void setElectricity_mm12(double electricity_mm12) {
        this.electricity_mm12 = electricity_mm12;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
    
