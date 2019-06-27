package com.yakov.coupons.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.yakov.coupons.javaBeans.Coupon;
import com.yakov.coupons.javaBeans.Customer;
import com.yakov.coupons.javaBeans.Purchase;

@Repository
public interface IPurchaseDAO extends CrudRepository<Purchase, Long> {

	void deleteByCustomerAndCoupon(Customer customer, Coupon coupon);

	void deleteByCoupon(Coupon coupond);

	void deleteAllByCustomer(Customer customer);

	List<Purchase> findAllByCustomer(Customer customer);

}
