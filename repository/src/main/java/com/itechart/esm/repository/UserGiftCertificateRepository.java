package com.itechart.esm.repository;

import com.itechart.esm.common.model.entity.UserGiftCertificate;

import java.util.List;
import java.util.Optional;

public interface UserGiftCertificateRepository {

	UserGiftCertificate save(UserGiftCertificate userGiftCertificate);

	List<UserGiftCertificate> findAll();

	Optional<UserGiftCertificate> findById(Long id);

	List<UserGiftCertificate> findByUserId(Long userId);

	List<UserGiftCertificate> findByGiftCertificateId(Long giftCertificateId);

	List<UserGiftCertificate> findByUserIdAndGiftCertificateId(Long userId, Long giftCertificateId);

}
