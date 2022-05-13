package com.itechart.esm.controller.security.service;

import com.itechart.esm.common.model.entity.User;
import org.springframework.security.core.AuthenticationException;

public interface SignService {

	String signIn(User user) throws AuthenticationException;

	boolean signUp(User user);

}
