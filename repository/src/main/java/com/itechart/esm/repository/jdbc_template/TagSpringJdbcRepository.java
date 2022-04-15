package com.itechart.esm.repository.jdbc_template;

import com.itechart.esm.repository.TagRepository;
import com.itechart.esm.repository.entity.Tag;
import com.itechart.esm.repository.mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class TagSpringJdbcRepository implements TagRepository {
	private static final String SELECT_ALL_TAG_QUERY
			= "SELECT * FROM tag";
	private static final String SELECT_TAG_BY_ID
			= "SELECT * FROM tag WHERE id =?";
	private static final String INSERT_TAG_QUERY
			= "INSERT INTO tag (name) VALUES (?)";
	private static final String DELETE_TAG_BY_ID
			= "DELETE FROM tag WHERE id = ?";
	private static final String SELECT_BY_NAME_QUERY
			= "SELECT * FROM tag WHERE name = ?";
	private static final String DELETE_ALL_QUERY
			= "DELETE FROM tag";

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public TagSpringJdbcRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}


	@Override
	public Tag save(Tag tag) {
		Optional<Tag> optionalTag = findByName(tag.getName());
		if (optionalTag.isPresent()) {
			return optionalTag.get();
		}
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(con -> {
			PreparedStatement preparedStatement = con.prepareStatement(INSERT_TAG_QUERY, new String[]{"id"});
			int i = 1; // number of parameter in the query
			preparedStatement.setString(i++, tag.getName());
			return preparedStatement;
		}, keyHolder);
		tag.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
		return tag;
	}

	@Override
	public List<Tag> findAll() {
		return jdbcTemplate.query(SELECT_ALL_TAG_QUERY, new TagMapper());
	}

	@Override
	public Optional<Tag> findById(Long id) {
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_TAG_BY_ID, new TagMapper(), id));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public Optional<Tag> findByName(String name) {
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_NAME_QUERY, new TagMapper(), name));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public boolean delete(Tag tag) {
		return jdbcTemplate.update(DELETE_TAG_BY_ID, tag.getId()) > 0;
	}

	@Override
	public boolean deleteById(Long id) {
		return jdbcTemplate.update(DELETE_TAG_BY_ID, id) > 0;
	}

	@Override
	public void deleteAll() {
		jdbcTemplate.update(DELETE_ALL_QUERY);
	}
}
