package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@OneToOne (mappedBy = "user", cascade = CascadeType.ALL)
	private UserMean userMean;

	@OneToOne (mappedBy = "user", cascade = CascadeType.ALL)
	private UserStd userStd;

	@OneToOne (mappedBy = "user", cascade = CascadeType.ALL)
	private GasMeanMonth gasMeanMonth;

	@OneToOne (mappedBy = "user", cascade = CascadeType.ALL)
	private ElectricityMeanMonth electricityMeanMonth;

	@OneToOne (mappedBy = "user", cascade = CascadeType.ALL)
	private InternetMeanMonth internetMeanMonth;

	@OneToOne (mappedBy = "user", cascade = CascadeType.ALL)
	private WaterMeanMonth waterMeanMonth;

	@Column(name = "first_name")
	private String first_name;

	@Column(name = "last_name")
	private String last_name;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	List<Bill> bills = new ArrayList<>();

	@Column(name = "total_bill")
	private Integer total_bill;

	public User(Integer total_bill) {
		this.total_bill = 0;
	}

	public User() {
	}

	public void setMeanMonthType(List<Double> amounts, String type, int month){
		switch (type.toLowerCase()){
			case "internet":
				internetMeanMonth.calculateInternetMeanMonth(amounts, month);
				break;
			case "electricity":
				electricityMeanMonth.calculateElectricityMeanMonth(amounts, month);
				break;
			case "gas":
				gasMeanMonth.calculateGasMeanMonth(amounts, month);
				break;
			case "water":
				waterMeanMonth.calculateWaterMeanMonth(amounts, month);
				break;
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UserMean getUserMean() {
		return userMean;
	}

	public void setUserMean(UserMean userMean) {
		this.userMean = userMean;
	}

	public UserStd getUserStd() {
		return userStd;
	}

	public void setUserStd(UserStd userStd) {
		this.userStd = userStd;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	@JsonIgnore
	public List<Bill> getBills() {
		return bills;
	}

	public void setBills(List<Bill> bills) {
		this.bills = bills;
	}

	@JsonIgnore
	public GasMeanMonth getGasMeanMonth() {
		return gasMeanMonth;
	}

	public void setGasMeanMonth(GasMeanMonth gasMeanMonth) {
		this.gasMeanMonth = gasMeanMonth;
	}

	@JsonIgnore
	public ElectricityMeanMonth getElectricityMeanMonth() {
		return electricityMeanMonth;
	}

	public void setElectricityMeanMonth(ElectricityMeanMonth electricityMeanMonth) {
		this.electricityMeanMonth = electricityMeanMonth;
	}

	@JsonIgnore
	public InternetMeanMonth getInternetMeanMonth() {
		return internetMeanMonth;
	}

	public void setInternetMeanMonth(InternetMeanMonth internetMeanMonth) {
		this.internetMeanMonth = internetMeanMonth;
	}

	@JsonIgnore
	public WaterMeanMonth getWaterMeanMonth() {
		return waterMeanMonth;
	}

	public void setWaterMeanMonth(WaterMeanMonth waterMeanMonth) {
		this.waterMeanMonth = waterMeanMonth;
	}

	public Integer getTotal_bill() {
		return total_bill;
	}

	public void setTotal_bill() {
		this.total_bill = getBills().size();
	}
}