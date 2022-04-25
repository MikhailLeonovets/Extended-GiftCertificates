package com.itechart.esm.controller.security.dto;

public class LogInReq {
	private String login;
	private String password;

	public LogInReq() {
	}

	public LogInReq(String login, String password) {
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
