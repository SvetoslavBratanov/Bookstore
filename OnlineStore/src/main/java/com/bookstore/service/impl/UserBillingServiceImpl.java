package com.bookstore.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.domain.User;
import com.bookstore.domain.UserBilling;
import com.bookstore.domain.UserPayment;
import com.bookstore.repository.UserBillingRepository;
import com.bookstore.repository.UserPaymentRepository;
import com.bookstore.repository.UserRepository;
import com.bookstore.service.UserBillingService;

@Service
public class UserBillingServiceImpl implements UserBillingService {

	@Autowired
	private UserBillingRepository userBillingRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserPaymentRepository userPaymentRepository;
		
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
	
	@Override
	public void updateUserBilling(UserBilling userBilling, UserPayment userPayment, User user) {
		for (int i = 0; i <user.getUserPaymentList().size(); i++) {
			if(userPayment.getId() == user.getUserPaymentList().get(i).getId()) {			
				userPaymentRepository.deleteById(userPayment.getId());
				user.getUserPaymentList().remove(i);
				userBillingRepository.deleteById(userBilling.getId() + 1);
				break;
			}
		}
		userPayment.setUser(user);
		userPayment.setUserBilling(userBilling);
		userPayment.setDefaultPayment(true);
		
		userBilling.setUserPayment(userPayment);
		
		user.getUserPaymentList().add(userPayment);
		userRepository.save(user);
	}
}
