package com.bookstore.service;

import com.bookstore.domain.UserBilling;

public interface UserBillingService {
	
	UserBilling findById(Long id);
	
	void removeById(Long id);
}
