package com.itechart.esm.controller;

import com.itechart.esm.common.model.entity.Role;
import com.itechart.esm.common.model.entity.User;
import com.itechart.esm.controller.security.dto.SignInReq;
import com.itechart.esm.controller.security.dto.SignUpReq;
import com.itechart.esm.controller.security.jwt.JwtUtils;
import com.itechart.esm.controller.security.service.model.UserDetailsImpl;
import com.itechart.esm.service.UserService;
import com.itechart.esm.service.exception.DataInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.itechart.esm.controller.storage.url.GiftCertificateUrl.URL_MAIN_GIFT_CERT_FILTER_PAGE;
import static com.itechart.esm.controller.storage.url.GiftCertificateUrl.URL_SIGN_IN;
import static com.itechart.esm.controller.storage.url.GiftCertificateUrl.URL_SIGN_OUT;
import static com.itechart.esm.controller.storage.url.GiftCertificateUrl.URL_SIGN_UP;

@RestController(URL_MAIN_GIFT_CERT_FILTER_PAGE)
public class AuthController {
	private final AuthenticationManager authenticationManager;
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtils jwtUtils;

	@Autowired
	public AuthController(AuthenticationManager authenticationManager,
	                      UserService userService,
	                      PasswordEncoder passwordEncoder,
	                      JwtUtils jwtUtils) {
		this.authenticationManager = authenticationManager;
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtils = jwtUtils;
	}

	@PostMapping(URL_SIGN_IN)
	public ResponseEntity<?> signIn(@RequestBody SignInReq signInReq) {
		Authentication authentication =
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInReq.getLogin(),
						signInReq.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
		List<String> roles = userDetails.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority).toList();
		return ResponseEntity.ok()
				.header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
				.body(userDetails);
	}

	@PostMapping(URL_SIGN_UP)
	public ResponseEntity<?> signUp(@RequestBody SignUpReq signUpReq) {
		User user = new User(signUpReq.getLogin(), signUpReq.getPassword());
		user.setRole(List.of(new Role("USER")));
		try {
			userService.save(user);
		} catch (DataInputException e) {
			return ResponseEntity.badRequest().body("INCORRECT INPUT OR LOGIN IS BUSY");
		}
		return ResponseEntity.ok().body("YOU ARE REGISTERED SUCCESSFULLY");
	}

	@PostMapping(URL_SIGN_OUT)
	public ResponseEntity<?> signOut() {
		ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
				.body("Ypu signed out");
	}
}
