package com.itechart.esm.controller.security.service;

import java.util.List;

public enum RoleAuthorityMapper {
	ADMIN(List.of()),
	USER(List.of()),
	GUEST(List.of());

	private final List<String> authorities;


	RoleAuthorityMapper(List<String> authorities) {
		this.authorities = authorities;
	}

	public List<String> getAuthorities() {
		return this.authorities;
	}
}
