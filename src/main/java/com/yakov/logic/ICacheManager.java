package com.yakov.coupons.logic;

import com.yakov.coupons.javaBeans.PostLoginUserData;

public interface ICacheManager {
	public void put(Integer key, PostLoginUserData value);

	public Object get(Integer key);

}
