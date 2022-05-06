package com.itechart.esm.controller.security.filter;

import com.itechart.esm.controller.security.service.impl.SecurityContextService;
import com.itechart.esm.service.exception.DataInputException;
import com.itechart.esm.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@Component
public class ActivateAccountFilter extends OncePerRequestFilter {
	private final SecurityContextService securityContextService;

	@Autowired
	public ActivateAccountFilter(SecurityContextService securityContextService) {
		this.securityContextService = securityContextService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)
					&& !securityContextService.getCurrentUser().isActive()) {
				response.setStatus(FORBIDDEN.value());
			}
			filterChain.doFilter(request, response);
		} catch (UserNotFoundException | DataInputException e) {
			e.printStackTrace();
		}
	}
}
