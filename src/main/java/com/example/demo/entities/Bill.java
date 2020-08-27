package com.example.demo.entities;


import javax.persistence.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "bill")
public class Bill{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "date")
    private Date date;

    @Column(name = "amount")
    private double amount;

    @Column(name = "type")
    private String type;

    @Column(name = "number")
    private String number;

    @Column(name = "location")
    private String location;

    public Bill() {
    }

    public List<Object> extract (){
        List<Object> results = new ArrayList<>();
        List<Bill> bills = getUser().getBills();
        List<Double> amounts = new ArrayList<>();
        if (!bills.isEmpty()){
            for (Bill bill : bills) {
                if (bill.getType().equalsIgnoreCase(getType())) {
                    amounts.add(bill.getAmount());
             }
            }
        }
        else {amounts.add(getAmount());}
        UserMean userMean = getUser().getUserMean();
        UserStd userStd = getUser().getUserStd();
        double mean = userMean.calculateMean(amounts, getType());
        userStd.calculateStd(amounts, mean, getType());
        results.add(userMean);
        results.add(userStd);
        return results;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDate() throws ParseException {
        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        return formatter.parse(formatter.format(date));
    }

    public void setDate(Date date) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        this.date = formatter.parse(formatter.format(date));
    }

}
