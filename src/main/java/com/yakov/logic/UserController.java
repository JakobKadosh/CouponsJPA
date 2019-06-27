package com.yakov.coupons.logic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.yakov.coupons.dao.IUserDAO;
import com.yakov.coupons.enums.ClientType;
import com.yakov.coupons.enums.ErrorType;
import com.yakov.coupons.exceptionHandling.MyException;
import com.yakov.coupons.javaBeans.Company;
import com.yakov.coupons.javaBeans.PostLoginUserData;
import com.yakov.coupons.javaBeans.User;
import com.yakov.coupons.javaBeans.UserLoginData;
import com.yakov.coupons.utils.InputChecker;

@Controller
public class UserController {
	@Autowired
	private IUserDAO userDAO;

	@Autowired
	private CompanyController companyController;

	@Autowired
	private MyCachManager cacheManager;

	public UserLoginData login(String email, String password) throws MyException {
		PostLoginUserData postlogin = generatePostLoginData(email, password);
		User user = userDAO.findByUserNameAndPassword(email, password);

		ClientType clientType = user.getClientType();
		int token = generateEncryptedToken(email);
		cacheManager.put(token, postlogin);

		UserLoginData userLoginData = new UserLoginData(postlogin.getUserId(), postlogin.getCompanId(), clientType,
				token);

		return userLoginData;
	}

	private PostLoginUserData generatePostLoginData(String userName, String password) throws MyException {
		User user = new User();
		user = userDAO.findByUserNameAndPassword(userName, password);
		if (user.getClientType() == ClientType.COMPANY) {

			PostLoginUserData postLoginUser = new PostLoginUserData(user.getUserId(), user.getCompany().getId(),
					user.getClientType());
			return postLoginUser;
		} else {

			PostLoginUserData postLoginUser = new PostLoginUserData(user.getUserId(), user.getClientType());
			return postLoginUser;
		}
	}

	// here we're doing something similar to encryption.
	// this technique work one way and cannot be decrypted
	private int generateEncryptedToken(String email) {
		int ran = (int) Math.random() * 1010;
		int ran2 = (int) Math.random() * 1010;
		String token = "Salt - junk data" + email + "Sheker kolshehu" + ran * ran2;
		return token.hashCode();
	}

	public long addUser(User user) throws MyException {
		if (isUserValidToAdd(user)) {
			return userDAO.save(user).getUserId();

		} else
			throw new MyException(ErrorType.GENERAL_ERROR, "faild to add user");
	}

	public User getUser(long userID) throws MyException {
		if (InputChecker.isValidId(userID)) {
			return userDAO.findById(userID).get();
		} else {
			throw new MyException(ErrorType.INVALID_ID, "invalid user id");
		}
	}

	public List<User> getAllUsers() {
		List<User> list = new ArrayList<User>();
		userDAO.findAll().forEach(user -> list.add(user));
		return list;
	}

	public List<User> getUsersByCompanyId(long companyId) throws MyException {
		if (InputChecker.isValidId(companyId)) {
			Company company = companyController.getCompanyById(companyId);

			return userDAO.findAllByCompany(company);
		} else {
			throw new MyException(ErrorType.INVALID_ID, "company id is invalid");
		}

	}

	public void deleteUser(long userID) throws MyException {
		if (InputChecker.isValidId(userID)) {
			userDAO.deleteById(userID);
		} else {
			throw new MyException(ErrorType.INVALID_ID, "invalid user id");
		}
	}

	public void updateUser(User user) throws MyException {
		if (isUserValidToUpdate(user)) {
			userDAO.save(user);
		} else
			throw new MyException(ErrorType.GENERAL_ERROR, "faild to update user");
	}

	private boolean isUserExistByName(String userName) throws MyException {

		return userDAO.existsByUserName(userName);
	}

	// private methods to be used locally for validations.

	// this will check the input data to be real
	// and also that the user is'nt register already
	private boolean isUserValidToAdd(User user) throws MyException {
		if (user == null) {
			throw new MyException(ErrorType.GENERAL_ERROR, "user is null!");
		} else if (isUserExistByName(user.getUserName())) {
			throw new MyException(ErrorType.GENERAL_ERROR, " user name exsits on the data base already");
		} else if (!InputChecker.isValidEmail(user.getUserName())) {
			throw new MyException(ErrorType.INVALID_EMAIL, "user mail address is invalid");
		} else if (!InputChecker.isValidPassword(user.getUserPassword())) {
			throw new MyException(ErrorType.INVALID_PASSWORD, "users password is invalid");
		}
		return true;
	}

	// this method will also check the authenticity of the data before updating in
	// the data base.
	private boolean isUserValidToUpdate(User user) throws MyException {
		if (user == null) {
			throw new MyException(ErrorType.GENERAL_ERROR, "user is null!");
		} else if (!InputChecker.isValidEmail(user.getUserName())) {
			throw new MyException(ErrorType.INVALID_EMAIL, "user mail address is invalid");
		} else if (!InputChecker.isValidPassword(user.getUserPassword())) {
			throw new MyException(ErrorType.INVALID_PASSWORD, "users password is invalid");
		}
		return true;
	}

}
