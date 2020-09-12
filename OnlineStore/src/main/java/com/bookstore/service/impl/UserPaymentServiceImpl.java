package com.bookstore.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.domain.UserPayment;
import com.bookstore.repository.UserPaymentRepository;
import com.bookstore.service.UserPaymentService;

@Service
public class UserPaymentServiceImpl implements UserPaymentService{

	@Autowired
	private UserPaymentRepository userPaymentRepository;
		
	public UserPayment findById(Long id) {
		Optional <UserPayment> userPaymentOptional = userPaymentRepository.findById(id);
		if(userPaymentOptional.isPresent()) {
			return userPaymentOptional.get();
		}		
		throw new RuntimeException("Payment with such ID doesn't exist!");
		//TODO change the error
	}
	
	
	public void removeById(Long id) {
		userPaymentRepository.deleteById(id);
	}
} 
