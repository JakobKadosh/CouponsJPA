package com.yakov.coupons.dailyJob;

import java.util.TimerTask;

import org.springframework.stereotype.Component;

import com.yakov.coupons.logic.CouponController;
import com.yakov.coupons.logic.PurchaseController;
@Component
public class MyTimerTask extends TimerTask {
	CouponController couponController=new CouponController();
	PurchaseController purchaseController=new PurchaseController();
	@Override
	public void run() {
		try {
			couponController.deleteExpCoupons();
		} catch (Exception e) {
			// Temporarily swallowing exception. 
			e.printStackTrace();
		}
	}
}
