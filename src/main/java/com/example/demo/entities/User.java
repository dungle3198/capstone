package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;


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

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@NotFound(action= NotFoundAction.IGNORE)
	List<BillData> billData = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@NotFound(action= NotFoundAction.IGNORE)
	List<UserStats> userStats = new ArrayList<>();

	@Column(name = "total_bill")
	private int totalBill;

//	@ManyToOne
//	@NotFound(action= NotFoundAction.IGNORE)
//	@JoinColumn(name = "cluster_id")
//	private Cluster cluster;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@NotFound(action= NotFoundAction.IGNORE)
	List<ClusterDetail> clusterDetails = new ArrayList<>();

	public User() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getTotalBill() {
		return totalBill;
	}

	public void setTotalBill(int totalBill) {
		this.totalBill = totalBill;
	}

//	public Cluster getCluster() {
//		return cluster;
//	}
//
//	public void setCluster(Cluster cluster) {
//		this.cluster = cluster;
//	}

	@JsonIgnore
	public List<ClusterDetail> getGroups() {
		return clusterDetails;
	}

	public void setGroups(List<ClusterDetail> clusterDetails) {
		this.clusterDetails = clusterDetails;
	}
}