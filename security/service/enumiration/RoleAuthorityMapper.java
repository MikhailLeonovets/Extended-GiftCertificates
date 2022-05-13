package com.itechart.esm.controller.security.service.enumiration;

import java.util.List;

import static com.itechart.esm.controller.security.service.enumiration.Authority.*;

public enum RoleAuthorityMapper {
	ADMIN(List.of(
			GUEST_AUTHORITY,
			USER_AUTHORITY,
			SYSTEM_ADMIN
	)),
	USER(List.of(
			GUEST_AUTHORITY,
			USER_AUTHORITY
	)),
	GUEST(List.of(
			GUEST_AUTHORITY
	));

	private final List<String> authorities;

	RoleAuthorityMapper(List<String> authorities) {
		this.authorities = authorities;
	}

	public List<String> getAuthorities() {
		return this.authorities;
	}
}
