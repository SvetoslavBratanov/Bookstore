package com.bookstore.service;

import com.bookstore.domain.User;
import com.bookstore.domain.UserBilling;
import com.bookstore.domain.UserPayment;

public interface UserBillingService {
	
	UserBilling findById(Long id);
	
	void removeById(Long id);
	
	void updateUserBilling(UserBilling userBilling, UserPayment userPayment, User user);
}
