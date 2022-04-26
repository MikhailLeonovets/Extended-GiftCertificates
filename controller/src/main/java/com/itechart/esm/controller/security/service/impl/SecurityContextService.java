package com.itechart.esm.controller.security.service.impl;

import com.itechart.esm.common.model.entity.User;
import com.itechart.esm.service.UserService;
import com.itechart.esm.service.exception.DataInputException;
import com.itechart.esm.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityContextService {
	private final UserService userService;

	@Autowired
	public SecurityContextService(UserService userService) {
		this.userService = userService;
	}

	public User getCurrentUser() throws UserNotFoundException, DataInputException {
		String login = SecurityContextHolder.getContext().getAuthentication().getName();
		return userService.findByLogin(login);
	}
}
