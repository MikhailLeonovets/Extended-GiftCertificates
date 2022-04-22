package com.itechart.esm.common.model.entity;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
	private Long id;
	private String login;
	private String password;
	private String role;
	private boolean isActive;

	public User() {
		this.isActive = true;
	}

	public User(Long id) {
		this.id = id;
		this.isActive = true;
	}

	public User(String login, String password) {
		this.login = login;
		this.password = password;
		this.isActive = true;
	}

	public User(Long id, String login, String password) {
		this.id = id;
		this.login = login;
		this.password = password;
		this.isActive = true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getRole() {
		return role;
	}

	public void setUserRole(String role) {
		this.role = role;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean active) {
		isActive = active;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return Objects.equals(id, user.id) && login.equals(user.login) && password.equals(user.password);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, login, password);
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", login='" + login + '\'' +
				", password='" + password + '\'' +
				'}';
	}
}
