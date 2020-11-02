package com.example.demo.entities;


import javax.persistence.*;

@Entity
@Table (name = "user_stats")
public class UserStats {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "category")
    private String category;

    @Column(name = "biller")
    private String biller;

    @Column(name = "number_of_bills")
    private int numberOfBills;

    @Column(name = "mean")
    private double mean;

    @Column(name = "standard_deviation")
    private double standardDeviation;

    public UserStats(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getNumberOfBills() {
        return numberOfBills;
    }

    public void setNumberOfBills(int numberOfBills) {
        this.numberOfBills = numberOfBills;
    }

    public double getMean() {
        return mean;
    }

    public void setMean(double mean) {
        this.mean = mean;
    }

    public double getStandardDeviation() {
        return standardDeviation;
    }

    public void setStandardDeviation(double standardDeviation) {
        this.standardDeviation = standardDeviation;
    }
}
