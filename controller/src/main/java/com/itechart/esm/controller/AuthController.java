package com.itechart.esm.controller;

import com.itechart.esm.common.model.entity.Role;
import com.itechart.esm.common.model.entity.User;
import com.itechart.esm.controller.security.dto.SignInReq;
import com.itechart.esm.controller.security.dto.SignUpReq;
import com.itechart.esm.controller.security.dto.UserAuthenticationResp;
import com.itechart.esm.controller.security.service.SignService;
import com.itechart.esm.service.UserService;
import com.itechart.esm.service.exception.DataInputException;
import com.itechart.esm.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.itechart.esm.controller.security.storage.MessageStorage.INCORRECT_EMAIL_OR_PASSWORD_MSG;
import static com.itechart.esm.controller.security.storage.MessageStorage.SIGN_OUT_FAILED_MSG;
import static com.itechart.esm.controller.security.storage.MessageStorage.SIGN_OUT_SUCCESS_MSG;
import static com.itechart.esm.controller.storage.url.GiftCertificateUrl.URL_MAIN_GIFT_CERT_PAGE;
import static com.itechart.esm.controller.storage.url.GiftCertificateUrl.URL_SIGN_IN;
import static com.itechart.esm.controller.storage.url.GiftCertificateUrl.URL_SIGN_OUT;
import static com.itechart.esm.controller.storage.url.GiftCertificateUrl.URL_SIGN_UP;

@RestController
@RequestMapping(URL_MAIN_GIFT_CERT_PAGE)
public class AuthController {
	private final SignService signService;

	@Autowired
	public AuthController(SignService signService) {
		this.signService = signService;
	}

	@PostMapping(URL_SIGN_IN)
	public ResponseEntity<?> signIn(@RequestBody SignInReq signInReq) {
		try {
			String accessToken = signService.signIn(new User(signInReq.getLogin(), signInReq.getPassword()));
			return ResponseEntity.ok(new UserAuthenticationResp(accessToken));
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().body(/*INCORRECT_EMAIL_OR_PASSWORD_MSG*/e.getMessage());
		}
	}

	@PostMapping(URL_SIGN_UP)
	public ResponseEntity<?> signUp(@RequestBody SignUpReq signUpReq) {
		User user = new User(signUpReq.getLogin(), signUpReq.getPassword());
		user.setRole(List.of(new Role("USER")));
		try {
			if (!signService.signUp(user)) {
				return ResponseEntity.badRequest().body("INCORRECT INPUT OR LOGIN IS BUSY");
			}
		} catch (UserNotFoundException | DataInputException e) {
			return ResponseEntity.badRequest().body("INCORRECT INPUT OR LOGIN IS BUSY");
		}
		return ResponseEntity.ok().body("YOU ARE REGISTERED SUCCESSFULLY");
	}

	@PostMapping(URL_SIGN_OUT)
	public ResponseEntity<?> signOut(HttpServletRequest request, HttpServletResponse response) {
		SecurityContextHolder.getContext().setAuthentication(null);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return ResponseEntity.badRequest().body(SIGN_OUT_FAILED_MSG);
		}
		new SecurityContextLogoutHandler().logout(request, response, authentication);
		return ResponseEntity.ok().body(SIGN_OUT_SUCCESS_MSG);
	}
}
