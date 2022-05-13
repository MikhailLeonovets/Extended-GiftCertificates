package com.itechart.esm.controller.security.service.model.enumiration;

import java.util.List;

import static com.itechart.esm.controller.security.service.model.enumiration.Authority.GUEST_AUTHORITY;
import static com.itechart.esm.controller.security.service.model.enumiration.Authority.SYSTEM_ADMIN;
import static com.itechart.esm.controller.security.service.model.enumiration.Authority.USER_AUTHORITY;

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
