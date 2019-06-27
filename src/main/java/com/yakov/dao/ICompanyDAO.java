package com.yakov.coupons.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.yakov.coupons.javaBeans.Company;

@Repository
public interface ICompanyDAO extends CrudRepository<Company, Long> {
	boolean existsByName(String name);

	List<Company> findByEmail(String email);

	Company findByName(String name);

}
