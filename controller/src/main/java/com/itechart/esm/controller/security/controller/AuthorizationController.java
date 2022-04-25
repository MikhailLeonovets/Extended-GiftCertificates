package com.itechart.esm.controller.security.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.itechart.esm.controller.security.dto.LogInReq;
import com.itechart.esm.controller.security.dto.UserAuthenticationResp;
import com.itechart.esm.controller.security.model.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Date;
import java.util.stream.Collectors;

import static com.itechart.esm.controller.security.storage.MessageStorage.INCORRECT_EMAIL_OR_PASSWORD_MSG;
import static com.itechart.esm.controller.security.storage.Storage.ROLE_CLAIM_STRING;
import static com.itechart.esm.controller.security.storage.Storage.SECRET_CLAIM_KEY_STRING;
import static com.itechart.esm.controller.security.storage.Storage.USERNAME_CLAIM;
import static com.itechart.esm.controller.security.storage.UrlStorage.LOGIN_POST_MAPPING;

@RestController
public class AuthorizationController {
	private final AuthenticationManager authenticationManager;

	@Autowired
	public AuthorizationController(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@PostMapping(LOGIN_POST_MAPPING)
	public ResponseEntity<?> authenticate(@RequestBody LogInReq requestDto) {
		Authentication authentication;
		try {
			authentication =
					authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestDto.getLogin(),
							requestDto.getPassword()));
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().body(INCORRECT_EMAIL_OR_PASSWORD_MSG);
		}
		UserDetailsImpl authenticatedUser = (UserDetailsImpl) authentication.getPrincipal();
		String accessToken = JWT.create()
				.withSubject(authenticatedUser.getUsername())
				.withExpiresAt(new Date(Long.MAX_VALUE))
				.withIssuer(ServletUriComponentsBuilder.fromCurrentRequest().toUriString())
				.withClaim(USERNAME_CLAIM, authenticatedUser.getUsername())
				.withClaim(ROLE_CLAIM_STRING,
						authenticatedUser.getAuthorities().stream()
								.map(SimpleGrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.sign(Algorithm.HMAC256(SECRET_CLAIM_KEY_STRING));
		return ResponseEntity.ok(new UserAuthenticationResp());
	}
}
