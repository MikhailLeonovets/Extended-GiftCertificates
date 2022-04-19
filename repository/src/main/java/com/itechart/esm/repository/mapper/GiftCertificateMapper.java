package com.itechart.esm.repository.mapper;

import com.itechart.esm.repository.entity.GiftCertificate;
import com.itechart.esm.repository.entity.builder.GiftCertificateBuilder;
import com.itechart.esm.utilities.converter.PostgresIntervalConverter;
import org.postgresql.util.PGInterval;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GiftCertificateMapper implements RowMapper<GiftCertificate> {
	@Override
	public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new GiftCertificateBuilder()
				.setId(rs.getLong("id"))
				.setName(rs.getString("name"))
				.setDescription(rs.getString("description"))
				.setPrice(rs.getBigDecimal("price"))
				.setDateOfCreation(rs.getTimestamp("date_of_creation").toLocalDateTime())
				.setDateOfModification(rs.getTimestamp("date_of_modification").toLocalDateTime())
				.setExpirationPeriod(PostgresIntervalConverter
						.convertPGIntervalToPeriod(rs.getObject("duration", PGInterval.class)))
				.build();
	}
}
