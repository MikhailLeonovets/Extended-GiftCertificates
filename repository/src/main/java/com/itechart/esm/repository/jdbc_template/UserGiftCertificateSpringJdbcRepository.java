package com.itechart.esm.repository.jdbc_template;

import com.itechart.esm.common.model.entity.GiftCertificate;
import com.itechart.esm.common.model.entity.UserGiftCertificate;
import com.itechart.esm.repository.UserGiftCertificateRepository;
import com.itechart.esm.repository.mapper.UserGiftCertificateMapper;
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

@Repository
public class UserGiftCertificateSpringJdbcRepository implements UserGiftCertificateRepository {
	private static final String INSERT_QUERY
			= "INSERT INTO app_user_gift_certificate (app_user_id, gift_certificate_id, price) VALUES (?, ?, ?)";
	private static final String FIND_ALL_QUERY
			= "SELECT * FROM app_user_gift_certificate";
	private static final String FIND_BY_ID_QUERY
			= "SELECT * FROM app_user_gift_certificate WHERE id = ?";
	private static final String FIND_BY_USER_ID_QUERY
			= "SELECT * FROM app_user_gift_certificate WHERE app_user_id = ?";
	private static final String FIND_BY_GIFT_CERTIFICATE_ID_QUERY
			= "SELECT * FROM app_user_gift_certificate WHERE gift_certificate_id = ?";
	private static final String FIND_BY_USER_ID_AND_GIFT_CERTIFICATE_ID_QUERY
			= "SELECT * FROM app_user_gift_certificate WHERE app_user_id = ? AND gift_certificate_id = ?";


	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public UserGiftCertificateSpringJdbcRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public UserGiftCertificate save(UserGiftCertificate userGiftCertificate) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(con -> {
			PreparedStatement preparedStatement = con.prepareStatement(INSERT_QUERY, new String[]{"id"});
			int i = 1; // number of parameter in query
			preparedStatement.setLong(i++, userGiftCertificate.getUser().getId());
			preparedStatement.setLong(i++, userGiftCertificate.getGiftCertificate().getId());
			preparedStatement.setBigDecimal(i++, userGiftCertificate.getPrice());
			return preparedStatement;
		}, keyHolder);
		userGiftCertificate.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
		return userGiftCertificate;
	}

	@Override
	public List<UserGiftCertificate> findAll() {
		return jdbcTemplate.query(FIND_ALL_QUERY, new UserGiftCertificateMapper());
	}

	@Override
	public Optional<UserGiftCertificate> findById(Long id) {
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID_QUERY,
					new UserGiftCertificateMapper(), id));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public List<UserGiftCertificate> findByUserId(Long userId) {
		return jdbcTemplate.query(FIND_BY_USER_ID_QUERY, new UserGiftCertificateMapper(), userId);
	}

	@Override
	public List<UserGiftCertificate> findByGiftCertificateId(Long giftCertificateId) {
		return jdbcTemplate.query(FIND_BY_GIFT_CERTIFICATE_ID_QUERY, new UserGiftCertificateMapper(),
				giftCertificateId);
	}

	@Override
	public List<UserGiftCertificate> findByUserIdAndGiftCertificateId(Long userId, Long giftCertificateId) {
		return jdbcTemplate.query(FIND_BY_USER_ID_AND_GIFT_CERTIFICATE_ID_QUERY, new UserGiftCertificateMapper(),
				userId, giftCertificateId);
	}
}
