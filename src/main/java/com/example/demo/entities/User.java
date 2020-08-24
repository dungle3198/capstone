package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@OneToOne (mappedBy = "user", cascade = CascadeType.ALL)
	private UserMean userMean;

	@OneToOne (mappedBy = "user", cascade = CascadeType.ALL)
	private UserStd userStd;

	@Column(name = "first_name")
	private String first_name;

	@Column(name = "last_name")
	private String last_name;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	List<Bill> bills = new ArrayList<>();

	public User() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@JsonIgnore
	public UserMean getUserMean() {
		return userMean;
	}

	public void setUserMean(UserMean userMean) {
		this.userMean = userMean;
	}

	@JsonIgnore
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
}