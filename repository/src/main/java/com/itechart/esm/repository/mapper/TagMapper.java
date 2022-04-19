package com.itechart.esm.repository.mapper;

import com.itechart.esm.repository.entity.Tag;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TagMapper implements RowMapper<Tag> {
	@Override
	public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Tag(rs.getLong("id"), rs.getString("name"));
	}
}
