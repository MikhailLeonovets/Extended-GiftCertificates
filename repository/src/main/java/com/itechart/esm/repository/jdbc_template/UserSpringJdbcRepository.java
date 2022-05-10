package com.itechart.esm.repository.jdbc_template;

import com.itechart.esm.common.model.entity.User;
import com.itechart.esm.repository.UserRepository;
import com.itechart.esm.repository.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class UserSpringJdbcRepository implements UserRepository {
	private static final String INSERT_QUERY
			= "INSERT INTO app_user (login, password, role) VALUES (?, ?, ?)";
	private static final String FIND_ALL_QUERY
			= "SELECT * FROM app_user";
	private static final String FIND_BY_ID_QUERY
			= "SELECT * FROM app_user WHERE id = ?";
	private static final String FIND_BY_LOGIN_QUERY
			= "SELECT * FROM app_user WHERE login = ?";
	private static final String UPDATE_QUERY
			= "UPDATE app_user SET " +
			"password = ?, " +
			"role = ? " +
			"WHERE id = ?";
	private static final String DELETE_QUERY
			= "DELETE FROM app_user WHERE id =?";

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public UserSpringJdbcRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public User save(User user) {
		Optional<User> optionalUser = findByLogin(user.getLogin());
		if (optionalUser.isPresent()) {
			return optionalUser.get();
		}
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(con -> {
			PreparedStatement preparedStatement = con.prepareStatement(INSERT_QUERY, new String[]{"id"});
			int i = 1;
			preparedStatement.setString(i++, user.getLogin());
			preparedStatement.setString(i++, user.getPassword());
			preparedStatement.setString(i++, user.getRole().stream().collect(Collectors.toList()).get(0).getRoleName());
			return preparedStatement;
		}, keyHolder);
		user.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
		return user;
	}

	@Override
	public List<User> findAll() {
		return jdbcTemplate.query(FIND_ALL_QUERY, new UserMapper());
	}

	@Override
	public Optional<User> findById(Long id) {
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID_QUERY, new UserMapper(), id));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public Optional<User> findByLogin(String login) {
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_LOGIN_QUERY, new UserMapper(), login));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public boolean update(User user) {
		return jdbcTemplate.update(UPDATE_QUERY,
				user.getPassword(),
				user.getRole(),
				user.getId()) > 0;
	}

	@Override
	public boolean delete(User user) {
		return deleteById(user.getId());
	}

	@Override
	public boolean deleteById(Long id) {
		return jdbcTemplate.update(DELETE_QUERY, id) > 0;
	}
}
