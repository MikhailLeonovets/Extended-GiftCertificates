package com.itechart.esm.service.impl;

import com.itechart.esm.repository.GiftCertificateRepository;
import com.itechart.esm.repository.entity.GiftCertificate;
import com.itechart.esm.service.GiftCertificateService;
import com.itechart.esm.service.exception.DataInputException;
import com.itechart.esm.service.exception.GiftCertificateNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
	private final GiftCertificateRepository giftCertificateRepository;

	@Autowired
	public GiftCertificateServiceImpl(GiftCertificateRepository giftCertificateRepository) {
		this.giftCertificateRepository = giftCertificateRepository;
	}

	@Override
	public void save(GiftCertificate giftCertificate) throws DataInputException {
		if (giftCertificate == null) {
			throw new DataInputException();
		}
		giftCertificateRepository.save(giftCertificate);
	}

	@Override
	public List<GiftCertificate> findAll() {
		return giftCertificateRepository.findAll();
	}

	@Override
	public GiftCertificate findById(Long id) throws GiftCertificateNotFoundException, DataInputException {
		if (id == null) {
			throw new DataInputException();
		}
		return giftCertificateRepository.findById(id).orElseThrow(GiftCertificateNotFoundException::new);
	}

	@Override
	public List<GiftCertificate> findByPartOfName(String name) throws DataInputException {
		if (name == null || name.isEmpty()) {
			throw new DataInputException();
		}
		return giftCertificateRepository.findByPartOfName(name);
	}

	@Override
	public List<GiftCertificate> findByPartOfDescription(String description) throws DataInputException {
		if (description == null || description.isEmpty()){
			throw new DataInputException();
		}
		return giftCertificateRepository.findByPartOfDescription(description);
	}

	@Override
	public void update(GiftCertificate giftCertificate) throws GiftCertificateNotFoundException, DataInputException {
		if (giftCertificate == null) {
			throw new DataInputException();
		}
		boolean isUpdated = giftCertificateRepository.update(giftCertificate);
		if (!isUpdated) {
			throw new GiftCertificateNotFoundException();
		}
	}

	@Override
	public void delete(GiftCertificate giftCertificate) throws GiftCertificateNotFoundException, DataInputException {
		if (giftCertificate == null) {
			throw new DataInputException();
		}
		boolean isDeleted = giftCertificateRepository.delete(giftCertificate);
		if (!isDeleted) {
			throw new GiftCertificateNotFoundException();
		}
	}

	@Override
	public void deleteById(Long id) throws GiftCertificateNotFoundException, DataInputException {
		if (id == null) {
			throw new DataInputException();
		}
		boolean isDeleted = giftCertificateRepository.deleteById(id);
		if (!isDeleted) {
			throw new GiftCertificateNotFoundException();
		}
	}
}
