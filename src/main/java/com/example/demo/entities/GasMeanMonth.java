package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    private double gas_mm1;

    @Column (name = "gas_mm2")
    private double gas_mm2;

    @Column (name = "gas_mm3")
    private double gas_mm3;

    @Column (name = "gas_mm4")
    private double gas_mm4;

    @Column (name = "gas_mm5")
    private double gas_mm5;

    @Column (name = "gas_mm6")
    private double gas_mm6;

    @Column (name = "gas_mm7")
    private double gas_mm7;

    @Column (name = "gas_mm8")
    private double gas_mm8;

    @Column (name = "gas_mm9")
    private double gas_mm9;

    @Column (name = "gas_mm10")
    private double gas_mm10;

    @Column (name = "gas_mm11")
    private double gas_mm11;

    @Column (name = "gas_mm12")
    private double gas_mm12;

    @OneToOne (optional = false)
    @JoinColumn (name = "user_id", nullable = false)
    private User user;

    public GasMeanMonth(){

    }

    public GasMeanMonth(int id) {
        this.gas_mm1 = 0;
        this.gas_mm2 = 0;
        this.gas_mm3 = 0;
        this.gas_mm4 = 0;
        this.gas_mm5 = 0;
        this.gas_mm6 = 0;
        this.gas_mm7 = 0;
        this.gas_mm8 = 0;
        this.gas_mm9 = 0;
        this.gas_mm10 = 0;
        this.gas_mm11 = 0;
        this.gas_mm12 = 0;
    }

    public void calculateGasMeanMonth (List<Double> amounts, int month){
        double mean = amounts.stream().mapToDouble(val -> val).average().orElse(0.0);
        switch (month) {
            case 1:
                setGas_mm1(mean);
                break;
            case 2:
                setGas_mm2(mean);
                break;
            case 3:
                setGas_mm3(mean);
                break;
            case 4:
                setGas_mm4(mean);
                break;
            case 5:
                setGas_mm5(mean);
                break;
            case 6:
                setGas_mm6(mean);
                break;
            case 7:
                setGas_mm7(mean);
                break;
            case 8:
                setGas_mm8(mean);
                break;
            case 9:
                setGas_mm9(mean);
                break;
            case 10:
                setGas_mm10(mean);
                break;
            case 11:
                setGas_mm11(mean);
                break;
            case 12:
                setGas_mm12(mean);
                break;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getGas_mm1() {
        return gas_mm1;
    }

    public void setGas_mm1(double gas_mm1) {
        this.gas_mm1 = gas_mm1;
    }

    public double getGas_mm2() {
        return gas_mm2;
    }

    public void setGas_mm2(double gas_mm2) {
        this.gas_mm2 = gas_mm2;
    }

    public double getGas_mm3() {
        return gas_mm3;
    }

    public void setGas_mm3(double gas_mm3) {
        this.gas_mm3 = gas_mm3;
    }

    public double getGas_mm4() {
        return gas_mm4;
    }

    public void setGas_mm4(double gas_mm4) {
        this.gas_mm4 = gas_mm4;
    }

    public double getGas_mm5() {
        return gas_mm5;
    }

    public void setGas_mm5(double gas_mm5) {
        this.gas_mm5 = gas_mm5;
    }

    public double getGas_mm6() {
        return gas_mm6;
    }

    public void setGas_mm6(double gas_mm6) {
        this.gas_mm6 = gas_mm6;
    }

    public double getGas_mm7() {
        return gas_mm7;
    }

    public void setGas_mm7(double gas_mm7) {
        this.gas_mm7 = gas_mm7;
    }

    public double getGas_mm8() {
        return gas_mm8;
    }

    public void setGas_mm8(double gas_mm8) {
        this.gas_mm8 = gas_mm8;
    }

    public double getGas_mm9() {
        return gas_mm9;
    }

    public void setGas_mm9(double gas_mm9) {
        this.gas_mm9 = gas_mm9;
    }

    public double getGas_mm10() {
        return gas_mm10;
    }

    public void setGas_mm10(double gas_mm10) {
        this.gas_mm10 = gas_mm10;
    }

    public double getGas_mm11() {
        return gas_mm11;
    }

    public void setGas_mm11(double gas_mm11) {
        this.gas_mm11 = gas_mm11;
    }

    public double getGas_mm12() {
        return gas_mm12;
    }

    public void setGas_mm12(double gas_mm12) {
        this.gas_mm12 = gas_mm12;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
