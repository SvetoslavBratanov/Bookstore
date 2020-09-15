package com.bookstore.service;

import com.bookstore.domain.User;
import com.bookstore.domain.UserPayment;

public interface UserPaymentService {
	UserPayment findById(Long id);
	
	void removeById(Long id);
	
	void setUserDefaultPayment(Long userPaymentId, User user);
}
