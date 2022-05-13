package com.itechart.esm.controller.security.jwt;

import com.itechart.esm.controller.security.service.model.UserDetailsImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import static com.itechart.esm.controller.storage.url.GiftCertificateUrl.URL_MAIN_GIFT_CERT_PAGE;

@Component
public class JwtUtils {
	@Value("${app.jwtCookieName}")
	private String jwtCookie;
	@Value("${app.jwtSecret}")
	private String jwtSecret;
	@Value("${app.jwtExpirationMs}")
	private String jwtExpirationMs;

	public String getJwtFromCookies(HttpServletRequest request) {
		Cookie cookie = WebUtils.getCookie(request, jwtCookie);
		if (cookie != null) {
			return cookie.getValue();
		} else {
			return null;
		}
	}

	public ResponseCookie generateJwtCookie(UserDetailsImpl userDetails) {
		String jwt = null;
		try {
			jwt = generateTokenFromUsername(userDetails.getUsername());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return ResponseCookie.from(jwtCookie, jwt)
				.path(URL_MAIN_GIFT_CERT_PAGE)
				.maxAge(24 * 60 * 60)
				.httpOnly(true).build();
	}

	public ResponseCookie getCleanJwtCookie() {
		return ResponseCookie.from(jwtCookie, null).path(URL_MAIN_GIFT_CERT_PAGE).build();
	}

	public String getUsernameFromJwtToken(String token) {
		return Jwts.parser()
				.setSigningKey(jwtSecret)
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}

	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException
				| MalformedJwtException
				| ExpiredJwtException
				| UnsupportedJwtException
				| IllegalArgumentException e) {
			return false;
		}
	}

	public String generateTokenFromUsername(String username) throws ParseException {
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(DateFormat.getDateInstance().parse((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}

}
