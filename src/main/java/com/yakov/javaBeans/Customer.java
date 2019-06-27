package com.yakov.coupons.javaBeans;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yakov.coupons.exceptionHandling.MyException;

@Entity
@Table(name = "customers")
public class Customer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Customer bean java file props setters getters constructor and a toString
	 * method regex for email validation
	 */
	// ---------------------prop----------------------------------

	@Id
	@Column(name = "customer_id", nullable = false, unique = true, columnDefinition = "BIGINT(20) UNSIGNED")
	private long customerId;

	@Column(name = "first_name", unique = true, nullable = false, length = 40)
	private String firstName;

	@Column(name = "last_name", unique = true, nullable = false, length = 40)
	private String lastName;

	@JoinColumn(name = "user")
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private User user;

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "customer", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Purchase> purchases;

	// ---------------------constructor--------------------------------
	/**
	 * 
	 * @param id        the customer id in the Customers table in the DB
	 * @param firstName customer's first name
	 * @param lastName  customer's last name
	 * @param email     customer's email
	 * @param password  customer's password
	 * @throws Exception general exception
	 * @see com.yakov.coupons.Coupons.exceptionHandling
	 */
	public Customer(long customerId, User user, String firstName, String lastName) {
		this(user, firstName, lastName);
		setCustomerId(customerId);
	}

	public Customer(User user, String firstName, String lastName) {
		super();
		setFirstName(firstName);
		setLastName(lastName);

		setUser(user);
	}

	public Customer() {
	}

	// ---------------------getters + setters-------------------------

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getFirstName() {
		return firstName;
	}

	/**
	 * 
	 * @param firstName customer's first name
	 * @throws MyException declared in exceptionHandling package
	 * @see com.yakov.coupons.Coupons.exceptionHandling.MyException
	 */
	public void setFirstName(String firstName) {

		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	// ---------------------methods--------------------------------
	@Override
	public String toString() {
		return "Customer ID: " + customerId + "\nFirst name: " + firstName + "\nLast name: " + lastName + "\nemail: "
				+ user.getUserName() + "\nPassword: " + user.getUserPassword();
	}

}
