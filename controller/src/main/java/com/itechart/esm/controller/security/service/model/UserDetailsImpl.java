package com.itechart.esm.controller.security.service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.itechart.esm.common.model.entity.User;
import com.itechart.esm.controller.security.service.model.enumiration.RoleAuthorityMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserDetailsImpl implements UserDetails {
	private Long id;
	private String login;
	@JsonIgnore
	private String password;
	private Collection<? extends GrantedAuthority> authorities;

	private boolean isActive = true;

	public UserDetailsImpl() {
	}

	public UserDetailsImpl(User user) {
		this.id = user.getId();
		this.login = user.getLogin();
		this.password = user.getPassword();
		this.authorities = getAuthorities(user);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.login;
	}

	@Override
	public boolean isAccountNonExpired() {
		return isActive;
	}

	@Override
	public boolean isAccountNonLocked() {
		return isActive;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return isActive;
	}

	@Override
	public boolean isEnabled() {
		return isActive;
	}

	private Collection<? extends GrantedAuthority> getAuthorities(User user) {
		String role = user.getRole().stream().findFirst().get().getRoleName();
		Collection<String> authorities = RoleAuthorityMapper.valueOf(role).getAuthorities();
		return authorities
				.stream()
				.map(SimpleGrantedAuthority::new)
				.toList();
	}
}
