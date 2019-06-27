package com.yakov.coupons.logic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.yakov.coupons.dao.ICustomerDAO;
import com.yakov.coupons.enums.ErrorType;
import com.yakov.coupons.exceptionHandling.MyException;
import com.yakov.coupons.javaBeans.Customer;
import com.yakov.coupons.utils.InputChecker;

@Controller
public class CustomerController {

	@Autowired
	private ICustomerDAO customerDAO;

	@Autowired
	private UserController userController;

	public Customer getCustomerByID(long customerID) throws MyException {
		if (!InputChecker.isValidId(customerID)) {
			throw new MyException(ErrorType.INVALID_ID, "customer id is invalid");
		}
		Customer customer = customerDAO.findById(customerID).get();
		if (customer != null) {
			return customer;
		} else
			throw new MyException(ErrorType.GENERAL_ERROR, ErrorType.GENERAL_ERROR.getInternalMessage());
	}

	public void updateCustomer(Customer customer) throws MyException {
		if (isCustomerValidToUpdate(customer)) {
			customerDAO.save(customer);
		} else {

			throw new MyException(ErrorType.GENERAL_ERROR, "Customer details are invalid");
		}
	}

	public long addCustomer(Customer customer) throws MyException {
		if (isCustomerValidToAdd(customer)) {
			// Adding a new user to the data base before adding him as a customer
			// plus setting the customer ID according to the added user.
			customer.setCustomerId(userController.addUser(customer.getUser()));
			customer = customerDAO.save(customer);
			return customer.getCustomerId();
		} else {
			System.out.println(customer);
			throw new MyException(ErrorType.GENERAL_ERROR,
					ErrorType.GENERAL_ERROR.getInternalMessage() + "\nCustomer details are invalid");
		}
	}

	public void deleteCustomer(long customerID) throws MyException {
		if (!InputChecker.isValidId(customerID)) {
			throw new MyException(ErrorType.INVALID_ID, "cutomer id is invalid");
		} else {
			customerDAO.deleteById(customerID);
		}
	}

	public List<Customer> getAllCustomers() throws MyException {
		List<Customer> list = new ArrayList<Customer>();
		customerDAO.findAll().forEach(customer -> list.add(customer));
		return list;
	}

	private boolean isCustomerValidToAdd(Customer customer) throws MyException {
		if (!InputChecker.isValidName(customer.getFirstName())) {
			throw new MyException(ErrorType.INVALID_NAME, "First name is invalid");
		} else if (!InputChecker.isValidName(customer.getLastName())) {
			throw new MyException(ErrorType.INVALID_NAME, "Last name is invalid");
		} else if (customer.getUser() == null) {
			throw new MyException(ErrorType.GENERAL_ERROR, "user object is null!");
		} else if (!InputChecker.isValidPassword(customer.getUser().getUserPassword())) {
			throw new MyException(ErrorType.INVALID_PASSWORD, "Password is invalid");
		}
		return true;
	}

	private boolean isCustomerValidToUpdate(Customer customer) throws MyException {
		if (!InputChecker.isValidName(customer.getFirstName())) {
			throw new MyException(ErrorType.INVALID_NAME, "First name is invalid");
		} else if (!InputChecker.isValidName(customer.getLastName())) {
			throw new MyException(ErrorType.INVALID_NAME, "Last name is invalid");
		} else if (customer.getUser() == null) {
			throw new MyException(ErrorType.GENERAL_ERROR, "user object is null!");
		} else if (!InputChecker.isValidPassword(customer.getUser().getUserPassword())) {
			throw new MyException(ErrorType.INVALID_PASSWORD, "Password is invalid");
		}
		return true;
	}
}
