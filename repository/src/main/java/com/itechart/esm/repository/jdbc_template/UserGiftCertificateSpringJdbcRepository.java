package com.itechart.esm.repository.jdbc_template;

import com.itechart.esm.common.model.entity.GiftCertificate;
import com.itechart.esm.common.model.entity.UserGiftCertificate;
import com.itechart.esm.repository.UserGiftCertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
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
	public UserGiftCertificate save(GiftCertificate giftCertificate) {

		return null;
	}

	@Override
	public List<UserGiftCertificate> findAll() {
		return null;
	}

	@Override
	public Optional<UserGiftCertificate> findById(Long id) {
		return Optional.empty();
	}

	@Override
	public List<UserGiftCertificate> findByUserId(Long userId) {
		return null;
	}

	@Override
	public List<UserGiftCertificate> findByGiftCertificateId(Long giftCertificateId) {
		return null;
	}

	@Override
	public List<UserGiftCertificate> findByUserIdAndGiftCertificateId(Long userId, Long giftCertificateId) {
		return null;
	}
}
