package test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.yakov.coupons.logic.CouponController;
import com.yakov.coupons.logic.CustomerController;
import com.yakov.coupons.logic.PurchaseController;
import com.yakov.coupons.logic.UserController;
import com.yakov.coupons.enums.CategoriesEnum;
import com.yakov.coupons.enums.ClientType;
import com.yakov.coupons.javaBeans.Company;
import com.yakov.coupons.javaBeans.Coupon;
import com.yakov.coupons.javaBeans.Customer;
import com.yakov.coupons.javaBeans.Purchase;
import com.yakov.coupons.javaBeans.User;
import com.yakov.coupons.logic.CompanyController;



public class Testing {
	public static void testIt() throws Exception {
		
		
		//getting instances of all the controllers. 
		CompanyController companyController = new CompanyController();
		CouponController couponController = new CouponController();
		CustomerController customerController = new CustomerController();
		PurchaseController purchaseController = new PurchaseController();
		UserController userController = new UserController();

		// Create company objects
		Company[] companies = new Company[5];
		companies[0] = new Company("Nike", "nike@nike.com", "NikePas8s");
		companies[1] = new Company("Bugstore", "bug@bug.com", "bugPass88");
		companies[2] = new Company("Ivory", "ivory@ivory.com", "Ivory153");
		companies[3] = new Company("HStern", "h-stern@hstern.com", "Hsten450");
		companies[4] = new Company("Castro", "castro@castro.com", "Castro120");
		
		// Add the companies to the data base
		for (Company company : companies) {
			companyController.addCompany(company);
		}

		// Delete company
		companyController.deleteCompany(5);
		// Update company
		companies[3].setEmail("info@hstern.com");
		
		companyController.updateCompany(companies[3]);
		// Read back from DB
		List<Company> companiesFromDB = companyController.getAllCompanies();
		if (companiesFromDB.size() == 4 && companiesFromDB.get(3).getEmail().equals("info@hstern.com")) {
			System.out.println("getting Companies from Data base --> Success !");
		} else {
			System.out.println("getting Companies from Data base--> Fail !");
		}
		
		// Create customer objects
		User[] users = new User[5];
		users[0] = new User("jbrock@cartoon.com", "jhonb123",companiesFromDB.get(3).getId() , ClientType.COMPANY);
		users[1] = new User("married2@nomaam.com", "4touchThem", companiesFromDB.get(2).getId() , ClientType.COMPANY);
		users[2] = new User("fred123@elm.com", "fkelm", companiesFromDB.get(3).getId() , ClientType.COMPANY);
		users[3] = new User("alissa@wonderland.com", "rabbit1", null, ClientType.CUSTOMER);
		users[4] = new User("hana@montana.com", "hmhm", companiesFromDB.get(1).getId() , ClientType.CUSTOMER);

		for (User user : users) {
			userController.addUser(user);
		}

		users[3].setUserName("alice@wonderland.com");
		userController.updateUser(users[3]);

		User userDB = userController.getUser(1);

		if (userDB != null) {
			System.out.println("Getting users from DB --> Success!");
		} else {
			System.out.println("Getting users from DB --> Faild!");
		}
		Customer[] customers = new Customer[5];
		customers[0] = new Customer(users[0].getUserId(),users[0],"Johnny", "Bravo", "jbrock@cartoon.com", "Jbrock123");
		customers[1] = new Customer(users[1].getUserId(),users[1],"Al", "Bundy", "married2@nomaam.com", "4TouchMe");
		customers[2] = new Customer(users[2].getUserId(),users[2],"Freddy", "Krueger", "fred123@elm.com", "Fkelm");
		customers[3] = new Customer(users[3].getUserId(),users[3],"Alissa", "Wonder", "alissa@wonderland.com", "Rabbit12");
		customers[4] = new Customer(users[4].getUserId(),users[4],"Hannah", "Montana", "hanale@montana.com", "Hmhm104");

		
		// Add the customers to the database
		for (Customer c : customers) {
			customerController.addCustomer(c);
		}
		// Delete customer
		customerController.deleteCustomer(5);
		// Update customer
		customers[3].setFirstName("Alice");
		customers[3].setLastName("Wonder");
		customers[3].setEmail("alice11@gmail.com");
//		 customers[3].setPassword("rabbit");
		customerController.updateCustomer(customers[3]);

		// Read back from DB
		List<Customer> customersFromDB = customerController.getAllCustomers();
		if (customersFromDB.size() == 4 && customersFromDB.get(3).getFirstName().equals("Alice")
				&& customersFromDB.get(3).getLastName().equals("Wonderland")
				&& customersFromDB.get(3).getEmail().equals("alice@wonderland.com")) {
			System.out.println("Getting Customers from Data Base  --> Success");
		} else {
			System.out.println("Getting Customers from Data Base --> Fail");
			
		}

		System.out.println("Testing company CRUD...");

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		/*
		 * Categories: 1- fashion 2- jewelry 3- computers
		 */

		// Create coupon objects
		Coupon[] coupons = new Coupon[6];

		// Nike T-shirt 50%, 50 coupons, 10$, 2019-02-01 through 2019-06-30
		Date startDate = formatter.parse("2019-02-01");
		Date endDate = formatter.parse("2019-06-30");
		coupons[0] = new Coupon(companies[0].getId(), CategoriesEnum.Baby, "T-Shirt half off!", "Get 50% off any Nike logo T-shirt",
				startDate, endDate, 50, 10.0, "https://i.ebayimg.com/images/g/YOsAAOSwoIlazLel/s-l300.jpg");

		// Nike Sneakers 20%, 50 coupons, 8$, 2019-02-01 through 2020-02-01
		startDate = formatter.parse("2019-02-01");
		endDate = formatter.parse("2020-02-01");
		coupons[1] = new Coupon(companies[0].getId(), CategoriesEnum.Snacks, "20% On Sneakers!", "Get 20% off any Nike Sneakers", startDate,
				endDate, 50, 8.0,
				"https://images.nike.com/is/image/DotCom/pwp_sheet2?$NIKE_PWPx3$&$img0=BQ8928_400&$img1=BQ8928_003&$img2=BQ8928_005");

		// Bug 30% off, 100 coupons, 5$, 2019-01-01 through 2019-12-31
		startDate = formatter.parse("2019-01-01");
		endDate = formatter.parse("2019-12-31");
		coupons[2] = new Coupon(companies[1].getId(), CategoriesEnum.HouseholdProducts, "30% OFF!", "Get 30% off any Bug Products", startDate, endDate,
				100, 7.5,
				"https://i.guim.co.uk/img/static/sys-images/Guardian/Pix/pictures/2012/2/1/1328121872902/A-computer-bug-007.jpg?width=700&quality=85&auto=format&fit=max&s=0070ca87239fdf83041ef0ad93db14c2");

		// Ivory mouse 50%, 20 coupons, 15.5$, 2019-02-01 through 2019-04-30
		startDate = formatter.parse("2019-02-01");
		endDate = formatter.parse("2019-04-30");
		coupons[3] = new Coupon(companies[2].getId(), CategoriesEnum.PaperProducts, "50% Off Laser Mouse", "Get 50% off any laser mouse",
				startDate, endDate, 20, 15.5, "https://www.arp.nl/webmedias/prb/460x460/58eb6cc34c2f85e0fda58cfa.jpg");

		// Ivory 1+1 on chargers, 30 coupons, 12$, 2019-01-01 through 2021-12-31
		startDate = formatter.parse("2019-01-01");
		endDate = formatter.parse("2021-12-31");
		coupons[4] = new Coupon(companies[2].getId(), CategoriesEnum.LaundrySupplies, "Buy 1 Get 1 Free on any Charger",
				"Buy 1 get 1 free on any phone charger", startDate, endDate, 30, 12,
				"https://4.imimg.com/data4/OG/CC/MY-7810515/mobile-phone-charger-500x500.jpg");

		// H Stern 30% off diamond necklaces, 10 coupons, 50$, 2019-01-01 through
		// 2019-12-31
		startDate = formatter.parse("2019-01-01");
		endDate = formatter.parse("2019-12-31");
		coupons[5] = new Coupon(companies[3].getId(), CategoriesEnum.CleaningSupplies, "Get 30% off Diamond Necklace",
				"Get 30% off selected diamond necklaces", startDate, endDate, 10, 50,
				"https://imagens.hstern.com.br/imagesmenu/menu_colec_copernicus.jpg");
		
		
		for (Coupon coupon : coupons) {
			couponController.addCoupon(coupon);
		}

		

		coupons[0].setAmount(60);
		couponController.updateCoupon(coupons[0]);
		couponController.deleteCoupon(2);

		// Read back from DB
		List<Coupon> couponsFromDB = couponController.getCompanysCoupons(1);
//		System.out.println(couponsFromDB+" test 144");
		if (couponsFromDB.size() == 1 && couponsFromDB.get(0).getAmount() == 60) {
			System.out.println("Getting Coupons from Data Base --> Success");
		} else {
			System.out.println("Getting Coupons from Data Base --> Fail");
		}

		System.out.println("Testing customer CRUD...");

		// Purchase coupons
		purchaseController.purchaseCoupon(customers[0].getCustomerId(), couponsFromDB.get(0).getId(), 1);
		purchaseController.purchaseCoupon(customers[1].getCustomerId(), 3, 5);
		purchaseController.purchaseCoupon(customers[2].getCustomerId(), 3, 5);
// purchaseController.deleteCouponPurchase(1,1);
// delete works great.
		
		// retrieve coupons from purchases table
		List<Purchase> customersCoupons =purchaseController.getAllPurchases() ;
		
		if (customersCoupons.size() > 0) {
			System.out.println("Purchase Success");
		} else {
			System.out.println("Purchase Fail");
		}
		
//		CouponExpritionDailyJob.deleteExpCoupon();
		

	}
//
//	public static void main(String[] args) throws Exception {
//		testIt();
//	}

}
