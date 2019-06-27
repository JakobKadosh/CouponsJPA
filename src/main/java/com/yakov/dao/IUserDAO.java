package com.yakov.coupons.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.yakov.coupons.exceptionHandling.MyException;
import com.yakov.coupons.javaBeans.Company;
import com.yakov.coupons.javaBeans.User;

@Repository
public interface IUserDAO extends CrudRepository<User, Long> {

	boolean existsByUserName(String userName);

	@Query("SELECT u FROM User u WHERE u.userName=:userName AND u.userPassword=:userPassword")
	User findByUserNameAndPassword(@Param("userName") String userName, @Param("userPassword") String password)
			throws MyException;

	List<User> findAllByCompany(Company company);

}
