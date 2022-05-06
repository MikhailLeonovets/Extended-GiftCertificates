package com.itechart.esm.repository.mapper;

import com.itechart.esm.common.model.entity.Role;
import com.itechart.esm.common.model.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserMapper implements RowMapper<User> {
	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		user.setId(rs.getLong("id"));
		user.setLogin(rs.getString("login"));
		user.setPassword(rs.getString("password"));
		user.setRole(List.of(new Role(rs.getString("role"))));
		return user;
	}
}
