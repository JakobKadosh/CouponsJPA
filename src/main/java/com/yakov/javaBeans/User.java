package com.yakov.coupons.javaBeans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.yakov.coupons.enums.ClientType;

@Entity
@Table(name = "users")
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// User variables
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id", unique = true, nullable = false)
	private Long userId;

	@Column(name = "user_name", unique = true, nullable = false)
	private String userName;

	@Column(name = "user_password", unique = true, nullable = false)
	private String userPassword;

	@Column(name = "client_type", nullable = false, unique = false, length = 40)
	@Enumerated(EnumType.STRING)
	private ClientType clientType;

	@ManyToOne

	private Company company;

	// constructors
	public User(Long userId, String userName, String userPassword, ClientType clientType) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userPassword = userPassword;
		this.clientType = clientType;

	}

	public User() {
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public ClientType getClientType() {
		return clientType;
	}

	public void setClientType(ClientType clientType) {
		this.clientType = clientType;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", userPassword=" + userPassword + ", clientType="
				+ clientType + ", company=" + company + "]";
	}

}
