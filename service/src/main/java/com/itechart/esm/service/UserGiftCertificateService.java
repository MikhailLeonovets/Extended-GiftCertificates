package com.itechart.esm.service;

import com.itechart.esm.common.model.entity.UserGiftCertificate;
import com.itechart.esm.service.exception.DataInputException;
import com.itechart.esm.service.exception.UserGiftCertificateNotFoundException;

import java.util.List;

public interface UserGiftCertificateService {

	UserGiftCertificate save(UserGiftCertificate userGiftCertificate) throws DataInputException;

	List<UserGiftCertificate> findAll();

	UserGiftCertificate findById(Long id) throws UserGiftCertificateNotFoundException, DataInputException;

	List<UserGiftCertificate> findByUserId(Long userId) throws DataInputException;

	List<UserGiftCertificate> findByGiftCertificateId(Long giftCertificateId) throws DataInputException;

	List<UserGiftCertificate> findByUserIdAndGiftCertificateId(Long userId, Long giftCertificateId) throws DataInputException;

}
