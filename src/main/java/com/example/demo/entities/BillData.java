package com.example.demo.entities;

import javax.persistence.*;

@Entity
@Table (name = "bill_data")
public class BillData {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private int id;

    @ManyToOne
    @JoinColumn (name = "user_id")
    private User user;

    @Column (name = "state")
    private String state;

    @Column (name = "month")
    private int month;

    @Column (name = "year")
    private int year;

    @Column (name = "category")
    private String category;

    @Column (name = "biller")
    private String biller;

    @Column (name = "amount")
    private double amount;

    @Column (name = "period")
    private int period;

    @Column (name = "monthly_amount")
    private double monthlyAmount;

    @Column (name = "predicted_amount")
    private double predictedAmount;

    @Column (name = "status")
    private boolean status;

//    @Column (name = "cluster")
//    private int cluster;

    public BillData() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBiller() {
        return biller;
    }

    public void setBiller(String biller) {
        this.biller = biller;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public double getMonthlyAmount() {
        return monthlyAmount;
    }

    public void setMonthlyAmount(double monthlyAmount) {
        this.monthlyAmount = monthlyAmount;
    }

    public double getPredictedAmount() {
        return predictedAmount;
    }

    public void setPredictedAmount(double predictedAmount) {
        this.predictedAmount = predictedAmount;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

//    public int getCluster() {
//        return cluster;
//    }
//
//    public void setCluster(int cluster) {
//        this.cluster = cluster;
//    }
}
