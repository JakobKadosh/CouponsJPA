package com.yakov.coupons.logic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yakov.coupons.dao.ICompanyDAO;
import com.yakov.coupons.dao.ICouponDAO;
import com.yakov.coupons.dao.ICustomerDAO;
import com.yakov.coupons.dao.IPurchaseDAO;
import com.yakov.coupons.enums.CategoriesEnum;
import com.yakov.coupons.enums.ErrorType;
import com.yakov.coupons.exceptionHandling.MyException;
import com.yakov.coupons.javaBeans.Company;
import com.yakov.coupons.javaBeans.Coupon;
import com.yakov.coupons.javaBeans.Customer;
import com.yakov.coupons.javaBeans.Purchase;
import com.yakov.coupons.utils.InputChecker;

@Repository
public class CouponController {

	@Autowired
	private ICouponDAO couponDAO;
	
	@Autowired
	private IPurchaseDAO purchaseDao;
	
	@Autowired
	private ICompanyDAO companyDao;
	
	@Autowired
	private ICustomerDAO customerDAO;

	public void updateCoupon(Coupon coupon) throws MyException {
		if (coupon != null) {

			couponDAO.save(coupon);
		} else {
			throw new MyException(ErrorType.GENERAL_ERROR, "coupon is null!!");
		}
	}

	public void addCoupon(Coupon coupon) throws MyException {
		// input validation
		if (isCouponValidToAdd(coupon)) {
			couponDAO.save(coupon);
		} else {
			throw new MyException(ErrorType.GENERAL_ERROR, "Faild to add Coupon");
		}
	}

	public Coupon getCouponById(long id) throws MyException {
		if (InputChecker.isValidId(id)) {
			Coupon coupon = couponDAO.findById(id).get();

			return coupon;
		} else {
			throw new MyException(ErrorType.INVALID_ID, "invlid id");
		}
	}

	public void deleteCoupon(long couponId) throws MyException {
		if (InputChecker.isValidId(couponId)) {
			couponDAO.deleteById(couponId);
		} else {
			throw new MyException(ErrorType.INVALID_ID, "invalid id");
		}
	}

	public List<Coupon> getAllCoupons() throws MyException {
		List<Coupon> list = new ArrayList<Coupon>();
		couponDAO.findAll().forEach(coupon -> list.add(coupon));

		return list;
	}

	public List<Coupon> getCompanysCoupons(long companyId) throws MyException {
		if (InputChecker.isValidId(companyId)) {
			List<Coupon> list = new ArrayList<Coupon>();
			Company company = companyDao.findById(companyId).get();
			couponDAO.findAllByCompanyId(companyId).forEach(coupon -> list.add(coupon));
			System.out.println(list + "Company: " + company);
			return list;
		} else
			throw new MyException(ErrorType.INVALID_ID, "invalid id");
	}

	public List<Coupon> getCompanysCouponsByCategory(long companyId, CategoriesEnum category) throws MyException {
		if (!InputChecker.isValidId(companyId)) {
			throw new MyException(ErrorType.INVALID_ID, "compnayId is not valid");
		} else {
			Company company = companyDao.findById(companyId).get();
			List<Coupon> list = new ArrayList<Coupon>();
			couponDAO.findAllByCompanyAndCategory(company, category).forEach(coupon -> list.add(coupon));

			return list;
		}
	}

	public List<Coupon> getCompanysCouponsByPrice(long companyId, double price) throws MyException {
		if (!InputChecker.isValidId(companyId)) {
			throw new MyException(ErrorType.INVALID_ID, "invalid id");
		} else if (!InputChecker.isValidPrice(price)) {
			throw new MyException(ErrorType.INVALID_PRICE, ErrorType.INVALID_PRICE.getInternalMessage());
		} else {
			Company company = companyDao.findById(companyId).get();
			List<Coupon> list = new ArrayList<Coupon>();
			couponDAO.findByCompanyAndPrice(company, price).forEach(coupon -> list.add(coupon));
			;
			return list;
		}
	}

	public List<Coupon> getCustomersCouponsByCategory(CategoriesEnum category, long customerId) throws MyException {
		if (!InputChecker.isValidId(customerId)) {
			throw new MyException(ErrorType.INVALID_ID, "customer ID is invalid");
		} else if (!InputChecker.isValidCategory(category)) {
			throw new MyException(ErrorType.INVALID_ID, "category is invalid");
		} else {
			List<Purchase> purchaseList = new ArrayList<Purchase>();
			Customer customer = customerDAO.findById(customerId).get();
			purchaseDao.findAllByCustomer(customer).forEach(purchase -> purchaseList.add(purchase));

			List<Coupon> couponList = new ArrayList<Coupon>();
			for (Purchase p : purchaseList) {
				couponList = couponDAO.findAllByCouponIdAndCategory(category, p.getCoupon().getId());
			}
			return couponList;
		}
	}

	public List<Coupon> getCustomerCouponsByPrice(long customerId, double price) throws MyException {
		if (!InputChecker.isValidId(customerId)) {
			throw new MyException(ErrorType.INVALID_ID, "customer ID is invalid");
		} else if (!InputChecker.isValidPrice(price)) {
			throw new MyException(ErrorType.INVALID_PRICE, ErrorType.INVALID_PRICE.getInternalMessage());
		} else {
			List<Purchase> purchaseList = new ArrayList<Purchase>();
			Customer customer = customerDAO.findById(customerId).get();

			purchaseList = purchaseDao.findAllByCustomer(customer);
			List<Coupon> couponList = new ArrayList<Coupon>();
			for (Purchase p : purchaseList) {
				couponList = couponDAO.findAllByCouponIdAndPrice(price, p.getCoupon().getId());
			}
			return couponList;
		}
	}

	public List<Coupon> getCustomerCoupons(long customerId) throws MyException {
		if (!InputChecker.isValidId(customerId)) {
			throw new MyException(ErrorType.INVALID_ID, ErrorType.INVALID_ID.getInternalMessage());
		}

		// to get all the customer purchases we need to get the objects for every
		// quarry.
		// so first i'm getting the purchase list and with that i can get the coupon
		// list.
		List<Purchase> purchaseList = new ArrayList<Purchase>();
		Customer customer = customerDAO.findById(customerId).get();

		purchaseDao.findAllByCustomer(customer).forEach(purchase -> purchaseList.add(purchase));
		List<Coupon> couponList = new ArrayList<Coupon>();
		for (Purchase purchase : purchaseList) {
			Coupon coupon = getCouponById(purchase.getCoupon().getId());
			couponList.add(coupon);
		}
		return couponList;
	}

	public void deleteExpCoupons() throws MyException, SQLException {
		List<Coupon> list = couponDAO.findByEndDateBefore(new Date());
		if (list != null) {
			couponDAO.deleteAll(list);
		}
	}

	private boolean isCouponValidToAdd(Coupon coupon) throws MyException {

		if (coupon == null) {
			throw new MyException(ErrorType.GENERAL_ERROR, "coupon is null");
		} else if (coupon.getStartDate().after(coupon.getEndDate()) || coupon.getEndDate().before(new Date())) {
			throw new MyException(ErrorType.INVALID_DATES, "coupon dates are invalid");
		} else if (coupon.getAmount() < 1) {
			throw new MyException(ErrorType.COUPON_IS_OUT_OF_ORDER, "coupon is out of order");
		} else if (coupon.getPrice() < 0) {
			throw new MyException(ErrorType.INVALID_PRICE, "coupon price is invalid");
		} else if (couponDAO.existsByTitle(coupon.getTitle())) {
			throw new MyException(ErrorType.GENERAL_ERROR, "coupon exsits on the data base");
		}
		return true;
	}

	public boolean isCouponAvailable(long couponId) {
		Coupon coupon = couponDAO.findById(couponId).get();
		if (coupon.getAmount() > 1 && coupon.getStartDate().before(new Date())
				&& coupon.getEndDate().after(new Date())) {
			return true;
		}
		return false;
	}
}
