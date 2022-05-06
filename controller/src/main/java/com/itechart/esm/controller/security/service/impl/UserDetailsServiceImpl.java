package com.itechart.esm.controller.security.service.impl;

import com.itechart.esm.common.model.entity.User;
import com.itechart.esm.controller.security.model.UserDetailsImpl;
import com.itechart.esm.controller.security.service.enumiration.RoleAuthorityMapper;
import com.itechart.esm.service.UserService;
import com.itechart.esm.service.exception.DataInputException;
import com.itechart.esm.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	private final UserService userService;

	@Autowired
	public UserDetailsServiceImpl(UserService userService) {
		this.userService = userService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			User user = userService.findByLogin(username);
			return new UserDetailsImpl(user.getLogin(),
					user.getPassword(),
					user.isActive(),
					getAuthorities(user));
		} catch (UserNotFoundException | DataInputException e) {
			throw new UsernameNotFoundException(e.getMessage());
		}
	}

	private List<SimpleGrantedAuthority> getAuthorities(User user) {
		String role = user.getRole().stream().findFirst().get().getRoleName();
		List<String> authorities = RoleAuthorityMapper.valueOf(role).getAuthorities();
		return authorities
				.stream()
				.map(SimpleGrantedAuthority::new)
				.toList();
	}
}
