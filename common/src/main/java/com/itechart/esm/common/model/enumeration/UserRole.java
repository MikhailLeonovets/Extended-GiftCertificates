package com.itechart.esm.common.model.enumeration;

import com.itechart.esm.common.model.exception.UserRoleNotFoundException;

public enum UserRole {
	SYSTEM_ADMIN("system_admin"),
	USER("user");

	private final String userRoleName;

	UserRole(String userRole) {
		this.userRoleName = userRole;
	}

	public String getUserRoleName() {
		return userRoleName;
	}

	public static UserRole getByUserRoleName(String userRoleName) throws UserRoleNotFoundException {
		for (UserRole userRole : values()) {
			if (userRole.getUserRoleName().equals(userRoleName)) {
				return userRole;
			}
		}
		throw new UserRoleNotFoundException();
	}
}
