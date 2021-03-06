package com.bookstore.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.domain.User;
import com.bookstore.domain.UserShipping;
import com.bookstore.repository.UserRepository;
import com.bookstore.repository.UserShippingRepository;
import com.bookstore.service.UserShippingService;

@Service
public class UserShippingServiceImpl implements UserShippingService {

	@Autowired
	private UserRepository userRepository;

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

	@Override
	public void updateUserShipping(UserShipping userShipping, User user) {
		userShipping.setUser(user);
		userShipping.setUserShippingDefault(true);
		user.getUserShippingList().add(userShipping);
		userRepository.save(user);
	}

	@Override
	public void setUserDefaultShipping(Long userShippingId, User user) {
		List<UserShipping> userShippingList = (List<UserShipping>) userShippingRepository.findAll();

		for (UserShipping userShipping : userShippingList) {
			if (userShipping.getId() == userShippingId) {
				userShipping.setUserShippingDefault(true);
				userShippingRepository.save(userShipping);
			} else {
				userShipping.setUserShippingDefault(false);
				userShippingRepository.save(userShipping);
			}
		}
	}

}
