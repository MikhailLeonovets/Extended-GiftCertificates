package com.itechart.esm.controller.security.controller;

import com.itechart.esm.common.model.entity.User;
import com.itechart.esm.controller.security.dto.SignInReq;
import com.itechart.esm.controller.security.dto.SignUpReq;
import com.itechart.esm.controller.security.dto.UserAuthenticationResp;
import com.itechart.esm.controller.security.service.SignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.itechart.esm.controller.security.storage.MessageStorage.INCORRECT_EMAIL_OR_PASSWORD_MSG;
import static com.itechart.esm.controller.security.storage.MessageStorage.LOGIN_IS_BUSY_MSG;
import static com.itechart.esm.controller.security.storage.MessageStorage.SIGNED_UP_SUCCESS_MSG;
import static com.itechart.esm.controller.security.storage.MessageStorage.SIGN_OUT_FAILED_MSG;
import static com.itechart.esm.controller.security.storage.MessageStorage.SIGN_OUT_SUCCESS_MSG;
import static com.itechart.esm.controller.security.storage.UrlStorage.SIGN_IN_POST_MAPPING;
import static com.itechart.esm.controller.security.storage.UrlStorage.SIGN_OUT_MAPPING;
import static com.itechart.esm.controller.security.storage.UrlStorage.SIGN_UP_POST_MAPPING;
import static com.itechart.esm.controller.storage.url.GiftCertificateUrl.URL_MAIN_AUTH_PAGE;
import static com.itechart.esm.controller.storage.url.GiftCertificateUrl.URL_MAIN_GIFT_CERT_PAGE;

@RestController
@RequestMapping(URL_MAIN_GIFT_CERT_PAGE + URL_MAIN_AUTH_PAGE)
public class AuthorizationController {
	private final SignService signService;

	@Autowired
	public AuthorizationController(SignService signService) {
		this.signService = signService;
	}

	@PostMapping(SIGN_IN_POST_MAPPING)
	public ResponseEntity<?> signIn(@RequestBody SignInReq requestDto) {
		try {
			String accessToken = signService.signIn(new User(requestDto.getLogin(), requestDto.getPassword()));
			return ResponseEntity.ok(new UserAuthenticationResp(accessToken));
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().body(INCORRECT_EMAIL_OR_PASSWORD_MSG);
		}
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

	@GetMapping(SIGN_OUT_MAPPING)
	public ResponseEntity<?> signOut(HttpServletRequest request, HttpServletResponse response) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return ResponseEntity.badRequest().body(SIGN_OUT_FAILED_MSG);
		}
		new SecurityContextLogoutHandler().logout(request, response, authentication);
		return ResponseEntity.ok().body(SIGN_OUT_SUCCESS_MSG);
	}
}
