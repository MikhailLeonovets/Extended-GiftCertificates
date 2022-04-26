package com.itechart.esm.controller.security.controller;

import com.itechart.esm.common.model.entity.User;
import com.itechart.esm.controller.security.dto.SignInReq;
import com.itechart.esm.controller.security.dto.SignUpReq;
import com.itechart.esm.controller.security.dto.UserAuthenticationResp;
import com.itechart.esm.controller.security.service.SignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.itechart.esm.controller.security.storage.MessageStorage.INCORRECT_EMAIL_OR_PASSWORD_MSG;
import static com.itechart.esm.controller.security.storage.MessageStorage.LOGIN_IS_BUSY_MSG;
import static com.itechart.esm.controller.security.storage.MessageStorage.SIGNED_UP_SUCCESS_MSG;
import static com.itechart.esm.controller.security.storage.UrlStorage.SIGN_IN_POST_MAPPING;
import static com.itechart.esm.controller.security.storage.UrlStorage.SIGN_UP_POST_MAPPING;

@RestController
public class AuthorizationController {
	private final SignService signService;

	@Autowired
	public AuthorizationController(SignService signService) {
		this.signService = signService;
	}

	@PostMapping(SIGN_IN_POST_MAPPING)
	public ResponseEntity<?> signIn(@RequestBody SignInReq requestDto) {
		try {
			signService.signIn(new User(requestDto.getLogin(), requestDto.getPassword()));
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().body(INCORRECT_EMAIL_OR_PASSWORD_MSG);
		}
		return ResponseEntity.ok(new UserAuthenticationResp());
	}

	@PostMapping(SIGN_UP_POST_MAPPING)
	public ResponseEntity<?> signUp(@RequestBody SignUpReq signUpReq) {
		User user = new User();
		user.setLogin(signUpReq.getLogin());
		user.setPassword(signUpReq.getPassword());
		boolean isDone = signService.signUp(user);
		if (isDone) {
			ResponseEntity.ok(SIGNED_UP_SUCCESS_MSG);
		}
		return ResponseEntity.badRequest().body(LOGIN_IS_BUSY_MSG);
	}
}
