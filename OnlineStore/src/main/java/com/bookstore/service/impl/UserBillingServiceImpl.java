package com.bookstore.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.bookstore.domain.UserBilling;
import com.bookstore.repository.UserBillingRepository;
import com.bookstore.service.UserBillingService;

public class UserBillingServiceImpl implements UserBillingService {

	@Autowired
	private UserBillingRepository userBillingRepository;
		
	public UserBilling findById(Long id) {
		Optional <UserBilling> userBillingOptional = userBillingRepository.findById(id);
		if(userBillingOptional.isPresent()) {
			return userBillingOptional.get();
		}		
		throw new RuntimeException("Billing with such ID doesn't exist!");
		//TODO change the error
	}

	@Override
	public void removeById(Long id) {
		userBillingRepository.deleteById(id);
		
	}
}
