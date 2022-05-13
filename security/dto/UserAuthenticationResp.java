package com.itechart.esm.controller.security.dto;

public class UserAuthenticationResp {
	private String jwt;

	public UserAuthenticationResp() {
	}

	public UserAuthenticationResp(String jwt) {
		this.jwt = jwt;
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
}
