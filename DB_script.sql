DROP DATABASE IF EXISTS `DbProjectPhase3`;
CREATE DATABASE `DbProjectPhase3`;
USE `DbProjectPhase3` ;
CREATE TABLE `companies`
( 
`company_id` BIGINT(3)AUTO_INCREMENT PRIMARY KEY,
`company_name` VARCHAR (12),
`email` VARCHAR(50),
`address` varchar(70)
)ENGIN=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

USE `DbProjectPhase3`;
CREATE TABLE `users`
(
`user_id` bigint(3) PRIMARY KEY AUTO_INCREMENT ,
`user_name` VARCHAR (25),
`company_id` bigint(3),
`client_type` VARCHAR (20),
`user_password` VARCHAR(20),
FOREIGN KEY (`company_id`) REFERENCES `companies`(`company_id`)ON DELETE CASCADE 
)ENGIN=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

USE `DbProjectPhase3`;
CREATE TABLE `customers`
(
`customer_id` bigint ,
`first_name` VARCHAR (15),
`last_name` VARCHAR(15),
`email` VARCHAR(50),
`password` VARCHAR (20),
FOREIGN KEY (`customer_id`) references `users` (`user_id`)ON DELETE CASCADE
)ENGIN=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

USE `DbProjectPhase3`;
CREATE TABLE `coupons`
(
`coupon_id` bigint(3) AUTO_INCREMENT PRIMARY KEY,
`company_id` bigint (3) ,
`category` varchar(15),
`title` VARCHAR(30),
`description` VARCHAR(250),
`start_date` DATE,
`end_date` DATE,
`amount` INT(4),
`price` DOUBLE,
`image` VARCHAR (200),
FOREIGN KEY (`company_id`) REFERENCES `companies`(`company_id`)ON DELETE CASCADE
)ENGIN=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

USE `DbProjectPhase3`;
CREATE TABLE `purchases`
(
`purchase_id` bigint(3) AUTO_INCREMENT PRIMARY KEY,
`customer_id` bigint(3),
`coupon_id` bigint(3),
`amount` int(4) , 
FOREIGN KEY (`customer_id`) REFERENCES `customers`(`customer_id`)ON DELETE CASCADE,
FOREIGN KEY(`coupon_id`) REFERENCES `coupons`(`coupon_id`)
)ENGIN=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1; 