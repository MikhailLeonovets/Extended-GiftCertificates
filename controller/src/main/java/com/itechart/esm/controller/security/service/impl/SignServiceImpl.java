package com.itechart.esm.controller.security.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.itechart.esm.common.model.entity.Role;
import com.itechart.esm.common.model.entity.User;
import com.itechart.esm.controller.security.model.UserDetailsImpl;
import com.itechart.esm.controller.security.service.SignService;
import com.itechart.esm.repository.UserRepository;
import com.itechart.esm.service.UserService;
import com.itechart.esm.service.exception.DataInputException;
import com.itechart.esm.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.itechart.esm.controller.security.storage.Storage.ROLE_CLAIM_STRING;
import static com.itechart.esm.controller.security.storage.Storage.SECRET_CLAIM_KEY_STRING;
import static com.itechart.esm.controller.security.storage.Storage.USERNAME_CLAIM;

@Service
public class SignServiceImpl implements SignService {
	private final AuthenticationManager authenticationManager;
	private final PasswordEncoder passwordEncoder;
	private final UserService userService;

	private static final String role = "user";

	@Autowired
	public SignServiceImpl(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder,
	                       UserService userService) {
		this.authenticationManager = authenticationManager;
		this.passwordEncoder = passwordEncoder;
		this.userService = userService;
	}

	@Override
	public String signIn(User user) throws AuthenticationException {
		Authentication authentication;
		authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getLogin(),
				user.getPassword()));
		UserDetailsImpl authenticatedUser = (UserDetailsImpl) authentication.getPrincipal();
		return JWT.create()
				.withSubject(authenticatedUser.getUsername())
				.withExpiresAt(new Date(Long.MAX_VALUE))
				.withIssuer(ServletUriComponentsBuilder.fromCurrentRequest().toUriString())
				.withClaim(USERNAME_CLAIM, authenticatedUser.getUsername())
				.withClaim(ROLE_CLAIM_STRING,
						authenticatedUser.getAuthorities().stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.sign(Algorithm.HMAC256(SECRET_CLAIM_KEY_STRING));
	}

	@Override
	public boolean signUp(User user) throws DataInputException {
		if (userService.isLoginBusy(user.getLogin())) {
			return false;
		}
		user.setRole(List.of(new Role(role)));
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userService.save(user);
		return true;
	}
}
