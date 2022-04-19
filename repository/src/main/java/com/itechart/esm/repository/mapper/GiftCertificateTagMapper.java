package com.itechart.esm.repository.mapper;

import com.itechart.esm.repository.entity.GiftCertificate;
import com.itechart.esm.repository.entity.GiftCertificateTag;
import com.itechart.esm.repository.entity.Tag;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GiftCertificateTagMapper implements RowMapper<GiftCertificateTag> {
	@Override
	public GiftCertificateTag mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new GiftCertificateTag(rs.getLong("id"),
				new GiftCertificate(rs.getLong("gift_certificate_id")),
				new Tag(rs.getLong("tag_id")));
	}
}
