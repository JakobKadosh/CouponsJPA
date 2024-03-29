package com.yakov.coupons.logic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.yakov.coupons.dao.ICompanyDAO;
import com.yakov.coupons.enums.ErrorType;
import com.yakov.coupons.exceptionHandling.MyException;
import com.yakov.coupons.javaBeans.Company;
import com.yakov.coupons.utils.InputChecker;

@Controller
public class CompanyController {
	// getting variables of all the dao in class level to be used in the controller
	// functionality
	@Autowired
	private ICompanyDAO companyDAO;

	// many of the functions have input validations to do before manipulating data
	// on the Data Base

	public long addCompany(Company company) throws MyException {
		if (!isCompanyValidToAdd(company)) {
			throw new MyException(ErrorType.GENERAL_ERROR, "Faild to add company");
		}

		Company c = companyDAO.save(company);
		return c.getId();
	}

	public void updateCompany(Company company) throws MyException {
		if (!isCompanyValidToUpdate(company)) {
			throw new MyException(ErrorType.GENERAL_ERROR, "Faild to update company");
		}
		companyDAO.save(company);

	}

	public void deleteCompany(long id) throws MyException {

		if (InputChecker.isValidId(id)) {
			Company company = getCompanyById(id);
			companyDAO.delete(company);
		} else {
			throw new MyException(ErrorType.INVALID_ID, "invalid id!");
		}
	}

	/**
	 * the following method checks: if the email is already in the DB, if the name
	 * is already in the DB, & if the format of the Company name, email, & password
	 * is valid.
	 */
	private boolean isCompanyValidToAdd(Company company) throws MyException {
		if (company == null) {
			throw new MyException(ErrorType.GENERAL_ERROR,
					new MyException(ErrorType.GENERAL_ERROR, "Company details are empty "), "company is null!!");
		} else if (companyDAO.existsByName(company.getName())) {
			throw new MyException(ErrorType.NAME_IS_ALREADY_EXISTS, "company exsits on the DB already");
		} else if (!InputChecker.isValidEmail(company.getEmail())) {
			throw new MyException(ErrorType.INVALID_EMAIL, "Email format Error");
		} else if (!InputChecker.isValidName(company.getName())) {
			throw new MyException(ErrorType.INVALID_NAME, "Name format Error");
		}

		return true;
	}

//this method checks if the company name is not gonna change due to update company. 
	private boolean isCompanyValidToUpdate(Company company) throws MyException {
		if (company == null) {
			throw new MyException(ErrorType.GENERAL_ERROR, "Error. Company details are empty");
		} else if (!companyDAO.findById(company.getId()).get().getName().equals(company.getName())) {
			throw new MyException(ErrorType.GENERAL_ERROR, "Sorry. updating the comapny name is forbidden");
		} else
			return true;
	}

	/*
	 * to delete company we need: delete all company's coupons using controller.
	 * delete users with company id using controller. now we can delete the company
	 * 
	 */

	public List<Company> getAllCompanies() throws MyException {
		List<Company> companies = new ArrayList<Company>();
		companyDAO.findAll().forEach(company -> companies.add(company));
		return companies;
	}

	public Company getCompanyByName(String companyName) throws MyException {
		if (InputChecker.isValidName(companyName)) {
			return companyDAO.findByName(companyName);
		}
		throw new MyException(ErrorType.INVALID_NAME, ErrorType.INVALID_NAME.getInternalMessage() + "Error");
	}

	public Company getCompanyById(long id) throws MyException {
		if (InputChecker.isValidId(id)) {
			Company company = companyDAO.findById(id).get();
			return company;
		} else {
			throw new MyException(ErrorType.INVALID_ID, "invalid company id");
		}
	}
}
