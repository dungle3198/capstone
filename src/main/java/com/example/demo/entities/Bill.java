package com.example.demo.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

    @Column(name = "month")
    private int month;

    public Bill() {
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

    public Date getDate()  {
        return date;
    }

    public void setDate(Date date) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        this.date = formatter.parse(formatter.format(date));
    }

    @JsonIgnore
    public int getMonth() {
        return month;
    }

    public void setMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        this.month = calendar.get(Calendar.MONTH) + 1;
    }
}
