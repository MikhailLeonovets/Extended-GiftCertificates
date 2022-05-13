package com.itechart.esm.controller.security.service;

import com.itechart.esm.common.model.entity.User;
import com.itechart.esm.controller.security.service.model.UserDetailsImpl;
import com.itechart.esm.service.UserService;
import com.itechart.esm.service.exception.DataInputException;
import com.itechart.esm.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	private final UserService userService;

	@Autowired
	public UserDetailsServiceImpl(UserService userService) {
		this.userService = userService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			User user = userService.findByLogin(username);
			return new UserDetailsImpl(user);
		} catch (UserNotFoundException | DataInputException e) {
			throw new UsernameNotFoundException(e.getMessage());
		}
	}
}
