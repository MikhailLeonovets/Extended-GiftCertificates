package com.itechart.esm.repository.jdbc_template;

import com.itechart.esm.common.model.entity.User;
import com.itechart.esm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class UserSpringJdbcRepository implements UserRepository {
	private static final String INSERT_QUERY
			= "INSERT INTO "user" (login, password, role) VALUES (?, ?, ?)";

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public UserSpringJdbcRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public User save(User user) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(con -> {
			PreparedStatement preparedStatement = con.prepareStatement(INSERT_QUERY, new String[]{"id"});
			int i = 1;
			preparedStatement.setString(i++, user.getLogin());
			preparedStatement.setString(i++, user.getPassword());
			preparedStatement.setString(i++, user.getRole());
			return preparedStatement;
		}, keyHolder);
		user.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
		return user;
	}

	@Override
	public List<User> findAll() {
		return null;
	}

	@Override
	public Optional<User> findById(Long id) {
		return Optional.empty();
	}

	@Override
	public User update(User user) {
		return null;
	}

	@Override
	public boolean delete(User user) {
		return false;
	}

	@Override
	public boolean deleteById(Long id) {
		return false;
	}
}
