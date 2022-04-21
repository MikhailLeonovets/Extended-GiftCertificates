package com.itechart.esm.repository;

import com.itechart.esm.common.model.entity.GiftCertificate;
import com.itechart.esm.common.model.entity.UserGiftCertificate;

import java.util.List;
import java.util.Optional;

public interface UserGiftCertificateRepository {

	UserGiftCertificate save(GiftCertificate giftCertificate);

	List<UserGiftCertificate> findAll();

	Optional<UserGiftCertificate> findById(Long id);

	List<UserGiftCertificate> findByUserId(Long userId);

	List<UserGiftCertificate> findByGiftCertificateId(Long giftCertificateId);

	List<UserGiftCertificate> findByUserIdAndGiftCertificateId(Long userId, Long giftCertificateId);

}
