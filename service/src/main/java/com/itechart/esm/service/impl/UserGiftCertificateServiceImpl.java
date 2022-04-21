package com.itechart.esm.service.impl;

import com.itechart.esm.common.model.entity.UserGiftCertificate;
import com.itechart.esm.repository.UserGiftCertificateRepository;
import com.itechart.esm.service.UserGiftCertificateService;
import com.itechart.esm.service.exception.DataInputException;
import com.itechart.esm.service.exception.UserGiftCertificateNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserGiftCertificateServiceImpl implements UserGiftCertificateService {
	private final UserGiftCertificateRepository userGiftCertificateRepository;

	@Autowired
	public UserGiftCertificateServiceImpl(UserGiftCertificateRepository userGiftCertificateRepository) {
		this.userGiftCertificateRepository = userGiftCertificateRepository;
	}

	@Override
	public UserGiftCertificate save(UserGiftCertificate userGiftCertificate) throws DataInputException {
		if (userGiftCertificate == null) {
			throw new DataInputException();
		}
		return userGiftCertificateRepository.save(userGiftCertificate);
	}

	@Override
	public List<UserGiftCertificate> findAll() {
		return userGiftCertificateRepository.findAll();
	}

	@Override
	public UserGiftCertificate findById(Long id) throws UserGiftCertificateNotFoundException, DataInputException {
		if (id == null) {
			throw new DataInputException();
		}
		Optional<UserGiftCertificate> optionalUserGiftCertificate = userGiftCertificateRepository.findById(id);
		if (optionalUserGiftCertificate.isEmpty()) {
			throw new UserGiftCertificateNotFoundException();
		}
		return optionalUserGiftCertificate.get();
	}

	@Override
	public List<UserGiftCertificate> findByUserId(Long userId) throws DataInputException {
		if (userId == null) {
			throw new DataInputException();
		}
		return userGiftCertificateRepository.findByUserId(userId);
	}

	@Override
	public List<UserGiftCertificate> findByGiftCertificateId(Long giftCertificateId) throws DataInputException {
		if (giftCertificateId == null) {
			throw new DataInputException();
		}
		return userGiftCertificateRepository.findByGiftCertificateId(giftCertificateId);
	}

	@Override
	public List<UserGiftCertificate> findByUserIdAndGiftCertificateId(Long userId, Long giftCertificateId)
			throws DataInputException {
		if (userId == null || giftCertificateId == null) {
			throw new DataInputException();
		}
		return userGiftCertificateRepository.findByUserIdAndGiftCertificateId(userId, giftCertificateId);
	}
}
