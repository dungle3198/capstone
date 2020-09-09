package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import javax.persistence.*;
import java.util.List;

@Entity
@Table (name = "gas_mean_month")
public class GasMeanMonth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column (name = "gas_mm1")
    private double gasMeanMonth1;

    @Column (name = "gas_mm2")
    private double gasMeanMonth2;

    @Column (name = "gas_mm3")
    private double gasMeanMonth3;

    @Column (name = "gas_mm4")
    private double gasMeanMonth4;

    @Column (name = "gas_mm5")
    private double gasMeanMonth5;

    @Column (name = "gas_mm6")
    private double gasMeanMonth6;

    @Column (name = "gas_mm7")
    private double gasMeanMonth7;

    @Column (name = "gas_mm8")
    private double gasMeanMonth8;

    @Column (name = "gas_mm9")
    private double gasMeanMonth9;

    @Column (name = "gas_mm10")
    private double gasMeanMonth10;

    @Column (name = "gas_mm11")
    private double gasMeanMonth11;

    @Column (name = "gas_mm12")
    private double gasMeanMonth12;

    @OneToOne (optional = false)
    @JoinColumn (name = "user_id", nullable = false)
    private User user;

    public GasMeanMonth(){

    }

    public GasMeanMonth(int id) {
        this.gasMeanMonth1 = 0;
        this.gasMeanMonth2 = 0;
        this.gasMeanMonth3 = 0;
        this.gasMeanMonth4 = 0;
        this.gasMeanMonth5 = 0;
        this.gasMeanMonth6 = 0;
        this.gasMeanMonth7 = 0;
        this.gasMeanMonth8 = 0;
        this.gasMeanMonth9 = 0;
        this.gasMeanMonth10 = 0;
        this.gasMeanMonth11 = 0;
        this.gasMeanMonth12 = 0;
    }

    public void calculateGasMeanMonth (List<Double> amounts, int month){
        DescriptiveStatistics stats = new DescriptiveStatistics();

        // Add the data from the array
        for (Double amount : amounts) {
            stats.addValue(amount);
        }
        //mean
        double mean = stats.getMean();
        switch (month) {
            case 1:
                setGasMeanMonth1(mean);
                break;
            case 2:
                setGasMeanMonth2(mean);
                break;
            case 3:
                setGasMeanMonth3(mean);
                break;
            case 4:
                setGasMeanMonth4(mean);
                break;
            case 5:
                setGasMeanMonth5(mean);
                break;
            case 6:
                setGasMeanMonth6(mean);
                break;
            case 7:
                setGasMeanMonth7(mean);
                break;
            case 8:
                setGasMeanMonth8(mean);
                break;
            case 9:
                setGasMeanMonth9(mean);
                break;
            case 10:
                setGasMeanMonth10(mean);
                break;
            case 11:
                setGasMeanMonth11(mean);
                break;
            case 12:
                setGasMeanMonth12(mean);
                break;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getGasMeanMonth1() {
        return gasMeanMonth1;
    }

    public void setGasMeanMonth1(double gasMeanMonth1) {
        this.gasMeanMonth1 = gasMeanMonth1;
    }

    public double getGasMeanMonth2() {
        return gasMeanMonth2;
    }

    public void setGasMeanMonth2(double gasMeanMonth2) {
        this.gasMeanMonth2 = gasMeanMonth2;
    }

    public double getGasMeanMonth3() {
        return gasMeanMonth3;
    }

    public void setGasMeanMonth3(double gasMeanMonth3) {
        this.gasMeanMonth3 = gasMeanMonth3;
    }

    public double getGasMeanMonth4() {
        return gasMeanMonth4;
    }

    public void setGasMeanMonth4(double gasMeanMonth4) {
        this.gasMeanMonth4 = gasMeanMonth4;
    }

    public double getGasMeanMonth5() {
        return gasMeanMonth5;
    }

    public void setGasMeanMonth5(double gasMeanMonth5) {
        this.gasMeanMonth5 = gasMeanMonth5;
    }

    public double getGasMeanMonth6() {
        return gasMeanMonth6;
    }

    public void setGasMeanMonth6(double gasMeanMonth6) {
        this.gasMeanMonth6 = gasMeanMonth6;
    }

    public double getGasMeanMonth7() {
        return gasMeanMonth7;
    }

    public void setGasMeanMonth7(double gasMeanMonth7) {
        this.gasMeanMonth7 = gasMeanMonth7;
    }

    public double getGasMeanMonth8() {
        return gasMeanMonth8;
    }

    public void setGasMeanMonth8(double gasMeanMonth8) {
        this.gasMeanMonth8 = gasMeanMonth8;
    }

    public double getGasMeanMonth9() {
        return gasMeanMonth9;
    }

    public void setGasMeanMonth9(double gasMeanMonth9) {
        this.gasMeanMonth9 = gasMeanMonth9;
    }

    public double getGasMeanMonth10() {
        return gasMeanMonth10;
    }

    public void setGasMeanMonth10(double gasMeanMonth10) {
        this.gasMeanMonth10 = gasMeanMonth10;
    }

    public double getGasMeanMonth11() {
        return gasMeanMonth11;
    }

    public void setGasMeanMonth11(double gasMeanMonth11) {
        this.gasMeanMonth11 = gasMeanMonth11;
    }

    public double getGasMeanMonth12() {
        return gasMeanMonth12;
    }

    public void setGasMeanMonth12(double gasMeanMonth12) {
        this.gasMeanMonth12 = gasMeanMonth12;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
