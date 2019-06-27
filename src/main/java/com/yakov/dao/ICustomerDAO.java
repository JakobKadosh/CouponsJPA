package com.yakov.coupons.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.yakov.coupons.javaBeans.Customer;

@Repository
public interface ICustomerDAO extends CrudRepository<Customer, Long> {

}
