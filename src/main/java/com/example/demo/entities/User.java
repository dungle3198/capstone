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

	@OneToOne (mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private UserMean userMean;

	@OneToOne (mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private UserStd userStd;

	@OneToOne (mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private GasMeanMonth gasMeanMonth;

	@OneToOne (mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private ElectricityMeanMonth electricityMeanMonth;

	@OneToOne (mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private InternetMeanMonth internetMeanMonth;

	@OneToOne (mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private WaterMeanMonth waterMeanMonth;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	List<BillData> billData = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	List<UserStats> userStats = new ArrayList<>();

	@Column(name = "total_bill")
	private int totalBill;

	@ManyToOne
	@JoinColumn(name = "cluster_id")
	private Cluster cluster;

	public User() {
	}

	public void setMeanMonthType(List<Double> amounts, String type, int month){
		switch (type.toLowerCase()){
			case "phone and internet":
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@JsonIgnore
	public List<BillData> getBillData() {
		return billData;
	}

	public void setBillData(List<BillData> billData) {
		this.billData = billData;
	}

	@JsonIgnore
	public List<UserStats> getUserStats() {
		return userStats;
	}

	public void setUserStats(List<UserStats> userStats) {
		this.userStats = userStats;
	}

	public GasMeanMonth getGasMeanMonth() {
		return gasMeanMonth;
	}

	public void setGasMeanMonth(GasMeanMonth gasMeanMonth) {
		this.gasMeanMonth = gasMeanMonth;
	}

	public ElectricityMeanMonth getElectricityMeanMonth() {
		return electricityMeanMonth;
	}

	public void setElectricityMeanMonth(ElectricityMeanMonth electricityMeanMonth) {
		this.electricityMeanMonth = electricityMeanMonth;
	}

	public InternetMeanMonth getInternetMeanMonth() {
		return internetMeanMonth;
	}

	public void setInternetMeanMonth(InternetMeanMonth internetMeanMonth) {
		this.internetMeanMonth = internetMeanMonth;
	}

	public WaterMeanMonth getWaterMeanMonth() {
		return waterMeanMonth;
	}

	public void setWaterMeanMonth(WaterMeanMonth waterMeanMonth) {
		this.waterMeanMonth = waterMeanMonth;
	}

	public int getTotalBill() {
		return totalBill;
	}

	public void setTotalBill(int totalBill) {
		this.totalBill = totalBill;
	}

	public Cluster getCluster() {
		return cluster;
	}

	public void setCluster(Cluster cluster) {
		this.cluster = cluster;
	}

//	public boolean isNewUser()
//	{
//		List<Bill> bills = getBills();
//		int electricityCount = 0;
//		int gasCount = 0;
//		int waterCount = 0;
//		int internetCount = 0;
//		for (Bill bill: bills) {
//			switch (bill.getType().toLowerCase())
//			{
//				case "phone and internet":
//					internetCount ++;
//					break;
//				case "gas":
//					gasCount++;
//					break;
//				case "water":
//					waterCount++;
//					break;
//				case "electricity":
//					electricityCount++;
//					break;
//			}
//		}
//		return internetCount < 5 && gasCount < 5 && waterCount < 5 && electricityCount < 5;
//	}
}