package com.itechart.esm.controller.security.dto;

public class SignUpReq {
	private String login;
	private String password;

	public SignUpReq() {
	}

	public SignUpReq(String login, String password) {
		this.login = login;
		this.password = password;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
