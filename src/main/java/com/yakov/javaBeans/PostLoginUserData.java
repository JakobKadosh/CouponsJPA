package com.yakov.coupons.javaBeans;

import com.yakov.coupons.enums.ClientType;

public class PostLoginUserData {

	private long userId, companyId;
	private ClientType clientType;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getCompanId() {
		return companyId;
	}

	public void setCompanId(long companId) {
		this.companyId = companId;
	}

	public ClientType getClientType() {
		return clientType;
	}

	public void setClientType(ClientType clientType) {
		this.clientType = clientType;
	}

	public PostLoginUserData(long userId, long companyId, ClientType clientType) {
		super();
		this.userId = userId;
		this.companyId = companyId;
		this.clientType = clientType;
	}

	public PostLoginUserData(long userId, ClientType clientType) {
		super();
		this.userId = userId;
		this.clientType = clientType;
	}

	@Override
	public String toString() {
		return "PostLoginUserData [userId=" + userId + ", companyId=" + companyId + "]";
	};

}
