package com.itechart.esm.repository.jdbc_template;

import com.itechart.esm.repository.GiftCertificateRepository;
import com.itechart.esm.repository.entity.GiftCertificate;
import com.itechart.esm.repository.mapper.GiftCertificateMapper;
import com.itechart.esm.utilities.converter.PostgresIntervalConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class GiftCertificateSpringJdbcRepository implements GiftCertificateRepository {
	private static final String INSERT_GIFT_CERTIFICATE_QUERY
			= "INSERT INTO gift_certificate (name, description, price, duration) " +
			"VALUES (?, ?, ?, ?)";
	private static final String SELECT_ALL_GIFT_CERTIFICATE_QUERY
			= "SELECT * FROM gift_certificate";
	private static final String SELECT_GIFT_CERTIFICATE_BY_ID_QUERY
			= "SELECT * FROM gift_certificate WHERE id = ?";
	private static final String UPDATE_GIFT_CERTIFICATE_QUERY
			= "UPDATE gift_certificate SET " +
			"name = ?, " +
			"description = ?, " +
			"price = ?, " +
			"date_of_modification = ?, " +
			"duration = ? " +
			"WHERE id =?";
	private static final String DELETE_GIFT_CERTIFICATE_BY_ID_QUERY
			= "DELETE FROM gift_certificate WHERE id = ?";
	private static final String FIND_GIFT_CERT_BY_PART_OF_NAME_QUERY
			= "SELECT * FROM gift_certificate WHERE name ~* ";
	private static final String FIND_GIFT_CERT_BY_PART_OF_DESCRIPTION_QUERY
			= "SELECT * FROM gift_certificate WHERE description ~* ";
	private static final String DELETE_ALL_QUERY
			= "DELETE FROM gift_certificate";

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public GiftCertificateSpringJdbcRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public GiftCertificate save(GiftCertificate giftCertificate) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(con -> {
			PreparedStatement preparedStatement = con.prepareStatement(INSERT_GIFT_CERTIFICATE_QUERY,
					new String[]{"id"});
			int i = 1; // number of parameter
			preparedStatement.setString(i++, giftCertificate.getName());
			preparedStatement.setString(i++, giftCertificate.getDescription());
			preparedStatement.setBigDecimal(i++, giftCertificate.getPrice());
			preparedStatement.setObject(i++,
					PostgresIntervalConverter.convertTimePeriodToPGInterval(giftCertificate.getExpirationPeriod()),
					Types.OTHER);
			return preparedStatement;
		}, keyHolder);
		giftCertificate.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
		return giftCertificate;
	}

	@Override
	public List<GiftCertificate> findAll() {
		return jdbcTemplate.query(SELECT_ALL_GIFT_CERTIFICATE_QUERY, new GiftCertificateMapper());
	}

	@Override
	public Optional<GiftCertificate> findById(Long id) {
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_GIFT_CERTIFICATE_BY_ID_QUERY,
					new GiftCertificateMapper(), id));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public List<GiftCertificate> findByPartOfName(String name) {
		String query = FIND_GIFT_CERT_BY_PART_OF_NAME_QUERY + "'" + name + "'";
		return jdbcTemplate.query(query, new GiftCertificateMapper());
	}

	@Override
	public List<GiftCertificate> findByPartOfDescription(String description) {
		String query = FIND_GIFT_CERT_BY_PART_OF_DESCRIPTION_QUERY + "'" + description + "'";
		return jdbcTemplate.query(query, new GiftCertificateMapper());
	}

	@Override
	public boolean update(GiftCertificate giftCertificate) {
		return jdbcTemplate.update(UPDATE_GIFT_CERTIFICATE_QUERY, giftCertificate.getName(),
				giftCertificate.getDescription(), giftCertificate.getPrice(),
				Timestamp.valueOf(LocalDateTime.now()),
				PostgresIntervalConverter.convertTimePeriodToPGInterval(giftCertificate.getExpirationPeriod()),
				giftCertificate.getId()) > 0;
	}

	@Override
	public boolean delete(GiftCertificate giftCertificate) {
		return jdbcTemplate.update(DELETE_GIFT_CERTIFICATE_BY_ID_QUERY, giftCertificate.getId()) > 0;
	}

	@Override
	public boolean deleteById(Long id) {
		return jdbcTemplate.update(DELETE_GIFT_CERTIFICATE_BY_ID_QUERY, id) > 0;
	}

	@Override
	public void deleteAll() {
		jdbcTemplate.update(DELETE_ALL_QUERY);
	}
}
