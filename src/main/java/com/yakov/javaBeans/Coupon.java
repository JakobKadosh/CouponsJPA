package com.yakov.coupons.javaBeans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yakov.coupons.enums.CategoriesEnum;
import com.yakov.coupons.exceptionHandling.MyException;

@Entity
@Table(name = "coupons")
public class Coupon implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * The coupon bean file, with exception handling, has props setters getters
	 * constructor and a toString method
	 */
	// ---------------------prop------------------
	@Id
	@GeneratedValue
	@Column(name = "coupon_id")
	private long id;

	@Column(name = "title", unique = true, nullable = false, length = 30)
	private String title;

	@Column(name = "description", nullable = false, length = 255)
	private String description;

	@Column(name = "image", nullable = false, length = 255)
	private String image;

	@Column(name = "start_date", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date startDate;

	@Column(name = "end_date", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date endDate;

	@Column(name = "price", nullable = false)
	private double price;

	@Column(name = "amount", nullable = false)
	private int amount;
	@Column(name = "category", nullable = false, length = 20)
	@Enumerated(EnumType.STRING)

	private CategoriesEnum category;

	@JoinColumn(name = "company", nullable = false, unique = false)
	@ManyToOne
	private Company company;

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "coupon", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Purchase> purchases;

	// ---------------------constructor---------------------
	/**
	 * Creates new coupon with the following parameters
	 * 
	 * @param id          the coupon's id
	 * @param companyId   the company's id
	 * @param amount      the amount of coupons
	 * @param title       the coupon title
	 * @param description the coupon description
	 * @param image       the url for an image of the coupon
	 * @param startDate   the start date of the coupon
	 * @param endDate     the end date of the coupon
	 * @param price       the coupon's price
	 * @param category    the enum category of the coupon
	 * @throws Exception general exception
	 * @see com.yakov.coupons.Coupons.exceptionHandling package.
	 */

	public Coupon(long id, String title, String description, String image, Date startDate, Date endDate, double price,
			int amount, CategoriesEnum category, Company company, List<Purchase> purchases) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.image = image;
		this.startDate = startDate;
		this.endDate = endDate;
		this.price = price;
		this.amount = amount;
		this.category = category;
		this.company = company;
		this.purchases = purchases;
	}

	public Coupon() {
	}

	// ---------------------getters + setters---------------------

	public long getId() {
		return id;
	}

	/**
	 * 
	 * @param id coupon id
	 * @throws InvalidIDException declared in exceptionHandling package
	 * @see exceptionHandling.InvalidIDException
	 */
	public void setId(long id) {
		this.id = id;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;

	}

	public String getDescription() {
		return description;
	}

	public CategoriesEnum getCategory() {
		return category;
	}

	public void setCategory(CategoriesEnum category) {
		this.category = category;
	}

	/**
	 * 
	 * @param description coupon's description
	 * @throws MyException declared in exceptionHandling package
	 * @see com.yakov.coupons.Coupons.exceptionHandling.MyException
	 */

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public List<Purchase> getPurchases() {
		return purchases;
	}

	public void setPurchases(List<Purchase> purchases) {
		this.purchases = purchases;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @param price coupon's price in double type
	 * @throws NegativeException declared in exceptionHandling package
	 * @see exceptionHandling.NegativeException
	 */

	// ---------------------methods--------------------------------
	@Override
	public String toString() {
		return "Coupon ID: " + id + "\nTitle: " + title + "\nDescription: " + description + "\nImage link: " + image
				+ "\nStart Date: " + startDate + "\nEnd Date: " + endDate + "\nAmount: " + amount + "\nPrice: " + price
				+ "$\nCategory: " + category + "Company:  " + company.toString() + "\nThank You. ";
	}
}
