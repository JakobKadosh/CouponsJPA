package com.yakov.coupons.logic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.yakov.coupons.dao.ICouponDAO;
import com.yakov.coupons.dao.IPurchaseDAO;
import com.yakov.coupons.enums.ErrorType;
import com.yakov.coupons.exceptionHandling.MyException;
import com.yakov.coupons.javaBeans.Coupon;
import com.yakov.coupons.javaBeans.Customer;
import com.yakov.coupons.javaBeans.Purchase;
import com.yakov.coupons.utils.InputChecker;

@Controller
public class PurchaseController {
//DI
	@Autowired
	private IPurchaseDAO purchasesDAO;
	@Autowired
	private ICouponDAO couponDao;
	@Autowired
	private CustomerController customerDao;

	public long purchaseCoupon(Purchase purchase) throws MyException {
		if (!isValidPurchase(purchase)) {
			throw new MyException(ErrorType.GENERAL_ERROR,
					ErrorType.GENERAL_ERROR.getInternalMessage() + "\nFaild to purchase");
		}
		// Reduce coupon stock according to purchase
		Coupon coupon = couponDao.findById(purchase.getCoupon().getId()).get();
		coupon.setAmount(coupon.getAmount() - purchase.getAmount());
		couponDao.save(coupon);
		purchase = purchasesDAO.save(purchase);
		return purchase.getPurchaseId();
	}

	public List<Purchase> getAllPurchases() throws MyException {
		List<Purchase> list = new ArrayList<Purchase>();
		purchasesDAO.findAll().forEach(purchase -> list.add(purchase));

		return list;
	}

	public Purchase getPurchaseById(long id) throws MyException {
		if (!InputChecker.isValidId(id)) {
			throw new MyException(ErrorType.INVALID_ID, "Invlid purchase id");
		}
		return purchasesDAO.findById(id).get();
	}

	public void deleteCouponPurchase(long userID, long couponID) throws MyException {
		if (!InputChecker.isValidId(userID)) {
			throw new MyException(ErrorType.INVALID_ID, "Invalid customer id");
		} else if (!InputChecker.isValidId(couponID)) {
			throw new MyException(ErrorType.INVALID_ID, "Invalid coupon  id");
		}
		Customer customer = customerDao.getCustomerByID(userID);
		Coupon coupon = couponDao.findById(couponID).get();

		purchasesDAO.deleteByCustomerAndCoupon(customer, coupon);
	}

	public void deletePurchaseByCouponID(long couponID) throws MyException {
		if (!InputChecker.isValidId(couponID)) {
			throw new MyException(ErrorType.INVALID_ID, "Invalid coupon id");
		}
		Coupon coupon = couponDao.findById(couponID).get();
		purchasesDAO.deleteByCoupon(coupon);
	}

	public void deleteCustomerPurchases(long customerID) throws MyException {
		if (!InputChecker.isValidId(customerID)) {
			throw new MyException(ErrorType.INVALID_ID, "Invalid customer id");
		}
		Customer customer = customerDao.getCustomerByID(customerID);
		purchasesDAO.deleteAllByCustomer(customer);
	}

	public List<Purchase> getCustomerPurchases(long customerId) throws MyException {
		Customer customer = customerDao.getCustomerByID(customerId);
		List<Purchase> list = new ArrayList<Purchase>();
		purchasesDAO.findAllByCustomer(customer).forEach(purchase -> list.add(purchase));
		return list;
	}

	public List<Coupon> getAllPurchasedCoupons() {
		List<Purchase> purchaseList = new ArrayList<Purchase>();
		purchasesDAO.findAll().forEach(purchase -> purchaseList.add(purchase));
		List<Coupon> list = new ArrayList<Coupon>();
		for (int i = 0; i < purchaseList.size(); i++) {
			list.add(couponDao.findById(purchaseList.get(i).getCoupon().getId()).get());
		}
		return list;
	}

	private boolean isValidPurchase(Purchase purchase) throws MyException {
		if (purchase.getCustomer() == null) {
			throw new MyException(ErrorType.GENERAL_ERROR,
					ErrorType.GENERAL_ERROR.getInternalMessage() + "\nCustomer object is null!");
		} else if (purchase.getCoupon() == null) {
			throw new MyException(ErrorType.GENERAL_ERROR,
					ErrorType.GENERAL_ERROR.getInternalMessage() + "\nCoupon object is null!");
		} else if (purchase.getAmount() < 1 || purchase.getAmount() > purchase.getCoupon().getAmount()) {
			throw new MyException(ErrorType.INVALID_AMOUNT,
					ErrorType.INVALID_AMOUNT.getInternalMessage() + "\nInvalid amount ");
		} else
			return true;
	}

}
