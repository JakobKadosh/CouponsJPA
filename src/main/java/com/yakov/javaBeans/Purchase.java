package com.yakov.coupons.javaBeans;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "purchases")
public class Purchase implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * customer vs coupon bean file for the db table, props setters getters
	 * constructor and a toString method
	 */
	// ---------------------prop----------------------------------
	@Id
	@GeneratedValue
	@Column(name = "purchase_id", unique = true, nullable = false)
	private long purchaseId;

	@JoinColumn(name = "customer", nullable = false, unique = false)
	@ManyToOne
	private Customer customer;

	@JoinColumn(name = "coupon", nullable = false, unique = false)
	@ManyToOne
	private Coupon coupon;

	@Column(name = "amount", nullable = false)
	private int amount;

	// ---------------------constructor--------------------------------
	/**
	 * 
	 * @param customerId on this table of all the purchased coupons this prop
	 *                   indicates the coupon's customer's id
	 * @param couponId   indicates the coupon's id
	 * @throws Exception general exception
	 */

	// ---------------------methods-------------------------

	public Purchase(long purchaseId, Customer customer, Coupon coupon, int amount) {
		super();
		this.purchaseId = purchaseId;
		this.customer = customer;
		this.coupon = coupon;
		this.amount = amount;
	}

	public Purchase(Customer customer, Coupon coupon, int amount) {
		super();
		this.customer = customer;
		this.coupon = coupon;
		this.amount = amount;
	}

	public Purchase() {
		super();
	}

	public long getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(long purchaseId) {
		this.purchaseId = purchaseId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Coupon getCoupon() {
		return coupon;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Purchase [purchaseId=" + purchaseId + ", customer=" + customer.toString() + ", coupon="
				+ coupon.toString() + ", amount=" + amount + "]";
	}

}
