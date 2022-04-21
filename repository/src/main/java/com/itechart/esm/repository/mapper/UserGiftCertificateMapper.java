package com.itechart.esm.repository.mapper;

import com.itechart.esm.common.model.entity.GiftCertificate;
import com.itechart.esm.common.model.entity.User;
import com.itechart.esm.common.model.entity.UserGiftCertificate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserGiftCertificateMapper implements RowMapper<UserGiftCertificate> {
	@Override
	public UserGiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserGiftCertificate userGiftCertificate = new UserGiftCertificate();
		userGiftCertificate.setId(rs.getLong("id"));
		userGiftCertificate.setUser(new User(rs.getLong("app_user_id")));
		userGiftCertificate.setGiftCertificate(new GiftCertificate(rs.getLong("gift_certificate_id")));
		userGiftCertificate.setPrice(rs.getBigDecimal("price"));
		return userGiftCertificate;
	}
}
