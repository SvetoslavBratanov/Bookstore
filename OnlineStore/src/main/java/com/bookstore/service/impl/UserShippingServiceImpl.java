package com.bookstore.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.domain.UserShipping;
import com.bookstore.repository.UserShippingRepository;
import com.bookstore.service.UserShippingService;

@Service
public class UserShippingServiceImpl implements UserShippingService {

	@Autowired
	private UserShippingRepository userShippingRepository;

	public UserShipping findById(Long id) {
		Optional<UserShipping> userShippingOptional = userShippingRepository.findById(id);
		if (userShippingOptional.isPresent()) {
			return userShippingOptional.get();
		}
		throw new RuntimeException("Shipping with such ID doesn't exist!");
		// TODO change the error
	}

	public void removeById(Long id) {
		userShippingRepository.deleteById(id);
	}

}
