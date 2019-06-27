package com.yakov.coupons.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.yakov.coupons.enums.CategoriesEnum;
import com.yakov.coupons.javaBeans.Company;
import com.yakov.coupons.javaBeans.Coupon;

@Repository
public interface ICouponDAO extends CrudRepository<Coupon, Long> {

	List<Coupon> findAllByTitle(String title);

	List<Coupon> findAllByCompanyId(long companyId);

	List<Coupon> findAllByCompanyAndCategory(Company company, CategoriesEnum category);

	@Query("SELECT c FROM Coupon c WHERE company=:company AND prive <= :price")
	List<Coupon> findByCompanyAndPrice(@Param("company") Company company, @Param("price") double price);

	@Query("SELECT c FROM Coupon c WHERE category=:category AND couponId=:couponId")
	List<Coupon> findAllByCouponIdAndCategory(@Param("category") CategoriesEnum category,
			@Param("couponId") long couponId);

	@Query("SELECT c FROM Coupon c WHERE price=:price AND couponId=:couponId")
	List<Coupon> findAllByCouponIdAndPrice(@Param("price") double price, @Param("couponId") long couponId);

	List<Coupon> findByEndDateBefore(Date endDate);

	boolean existsByTitle(String title);

}
