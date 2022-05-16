package com.itechart.esm.controller.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.itechart.esm.controller.security.storage.Storage.AUTHORIZATION_HEADER_STARTS_WITH_STRING;
import static com.itechart.esm.controller.security.storage.Storage.ROLE_CLAIM_STRING;
import static com.itechart.esm.controller.security.storage.Storage.SECRET_CLAIM_KEY_STRING;
import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authorizationHeader = request.getHeader(AUTHORIZATION);
		if (authorizationHeader != null
				&& SecurityContextHolder.getContext().getAuthentication() == null
				&& authorizationHeader.startsWith(AUTHORIZATION_HEADER_STARTS_WITH_STRING)) {
			try {
				String token = authorizationHeader.substring("Bearer ".length());
				Algorithm algorithm = Algorithm.HMAC256(SECRET_CLAIM_KEY_STRING.getBytes());
				JWTVerifier verifier = JWT.require(algorithm).build();
				DecodedJWT decodedJWT = verifier.verify(token);

				String username = decodedJWT.getSubject();
				String[] roles = decodedJWT.getClaim(ROLE_CLAIM_STRING).asArray(String.class);
				List<SimpleGrantedAuthority> authorities = stream(roles)
						.map(SimpleGrantedAuthority::new)
						.toList();

				UsernamePasswordAuthenticationToken authenticationToken
						= new UsernamePasswordAuthenticationToken(username, null, authorities);
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			} catch (Exception e) {
				response.setStatus(FORBIDDEN.value());
				Map<String, String> errors = new HashMap<>();
				errors.put("errorMessage", e.getMessage());
				new ObjectMapper().writeValue(response.getOutputStream(), errors);
			}
		}
		filterChain.doFilter(request, response);
	}
}
