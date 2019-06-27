package com.yakov.coupons.javaBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "companies")
@JsonIgnoreProperties({ "companiesCoupons", "users" })
public class Company implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Company bean file with exception handling
	 */
	// ----------------------props------------------
	@Id
	@GeneratedValue
	@Column(name = "company_id")
	private long id;

	@Column(name = "company_name", unique = true, nullable = false, length = 40)
	private String name;

	@Column(name = "email", unique = true, nullable = false, length = 40)
	private String email;

	@Column(name = ("address"), unique = true, nullable = false, length = 100)
	private String address;

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "company", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Coupon> companyCoupons = new ArrayList<Coupon>();

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "company", fetch = FetchType.LAZY)
	private List<User> users = new ArrayList<User>();

	// ----------------------constructors-----------
	/**
	 * 
	 * @param id              - Company id in the Companies table in the database
	 * @param name            - Company name
	 * @param email           - Company email address
	 * @param password        - Company's password to access system
	 * @param companysCoupons - An ArrayList of all the company's coupons
	 * @throws Exception general exception
	 * @see com.yakov.coupons.Coupons.exceptionHandling
	 */

	public Company(long id, String name, String email, String address, List<Coupon> companyCoupons, List<User> users) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.address = address;
		this.companyCoupons = companyCoupons;
		this.users = users;
	}

	public Company() {
	}

	// ----------------------getters + setters-----------

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setCompanyCoupons(List<Coupon> companyCoupons) {
		this.companyCoupons = companyCoupons;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Coupon> getCompanyCoupons() {
		return companyCoupons;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Coupon> getCompaniesCoupons() {
		return companyCoupons;
	}

	public void setCompanyCoupons(ArrayList<Coupon> companyCoupons) {
		this.companyCoupons = companyCoupons;
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + ", email=" + email + ", address=" + address + "]";
	}

}
