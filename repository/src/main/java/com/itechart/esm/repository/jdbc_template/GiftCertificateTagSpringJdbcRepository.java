package com.itechart.esm.repository.jdbc_template;

import com.itechart.esm.repository.GiftCertificateTagRepository;
import com.itechart.esm.repository.entity.GiftCertificate;
import com.itechart.esm.repository.entity.GiftCertificateTag;
import com.itechart.esm.repository.entity.Tag;
import com.itechart.esm.repository.mapper.GiftCertificateTagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class GiftCertificateTagSpringJdbcRepository implements GiftCertificateTagRepository {
	private static final String INSERT_QUERY = "INSERT INTO gift_certificate_tag (gift_certificate_id, tag_id) " +
			"VALUES (?, ?)";
	private static final String SELECT_ALL_QUERY = "SELECT * FROM gift_certificate_tag";
	private static final String SELECT_BY_ID_QUERY = "SELECT * FROM gift_certificate_tag WHERE id = ?";
	private static final String SELECT_BY_TAG_QUERY = "SELECT * FROM gift_certificate_tag WHERE tag_id = ?";
	private static final String SELECT_BY_GIFT_CERTIFICATE_QUERY = "SELECT * FROM gift_certificate_tag " +
			"WHERE gift_certificate_id = ?";
	private static final String SELECT_BY_TAG_ID_AND_GIFT_CERTIFICATE_ID_QUERY
			= "SELECT * FROM gift_certificate_tag WHERE tag_id=? AND gift_certificate_id=?";
	private static final String DELETE_BY_ID_QUERY = "DELETE FROM gift_certificate_tag WHERE id = ?";


	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public GiftCertificateTagSpringJdbcRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public GiftCertificateTag save(GiftCertificateTag giftCertificateTag) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(con -> {
			PreparedStatement preparedStatement = con.prepareStatement(INSERT_QUERY, new String[]{"id"});
			int i = 1; // number of parameter in the query
			preparedStatement.setLong(i++, giftCertificateTag.getGiftCertificate().getId());
			preparedStatement.setLong(i++, giftCertificateTag.getTag().getId());
			return preparedStatement;
		}, keyHolder);
		giftCertificateTag.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
		return giftCertificateTag;
	}

	@Override
	public List<GiftCertificateTag> findAll() {
		return jdbcTemplate.query(SELECT_ALL_QUERY, new GiftCertificateTagMapper());
	}

	@Override
	public Optional<GiftCertificateTag> findById(Long id) {
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_ID_QUERY,
					new GiftCertificateTagMapper(), id));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public List<GiftCertificateTag> findByTag(Tag tag) {
		return jdbcTemplate.query(SELECT_BY_TAG_QUERY, new GiftCertificateTagMapper(), tag.getId());
	}

	@Override
	public List<GiftCertificateTag> findByGiftCertificate(GiftCertificate giftCertificate) {
		return jdbcTemplate.query(SELECT_BY_GIFT_CERTIFICATE_QUERY, new GiftCertificateTagMapper(),
				giftCertificate.getId());
	}

	@Override
	public Optional<GiftCertificateTag> findByTagIdAndGiftCertificateId(Long tagId, Long giftCertificateId) {
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_TAG_ID_AND_GIFT_CERTIFICATE_ID_QUERY,
					new GiftCertificateTagMapper(),
					tagId, giftCertificateId));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public boolean delete(GiftCertificateTag giftCertificateTag) {
		return jdbcTemplate.update(DELETE_BY_ID_QUERY, giftCertificateTag.getId()) > 0;
	}

	@Override
	public boolean deleteById(Long id) {
		return jdbcTemplate.update(DELETE_BY_ID_QUERY, id) > 0;
	}
}
