package com.itechart.esm.service;

import com.itechart.esm.repository.entity.GiftCertificate;
import com.itechart.esm.service.exception.DataInputException;
import com.itechart.esm.service.exception.GiftCertificateNotFoundException;

import java.util.List;

public interface GiftCertificateService {

	/**
	 * @param giftCertificate is to be saved in the database
	 * @throws DataInputException when giftCertificate is null
	 */
	void save(GiftCertificate giftCertificate) throws DataInputException;

	/**
	 * @return list of all stored GiftCertificates in the database
	 */
	List<GiftCertificate> findAll();

	/**
	 * @param id is for searching stored GiftCertificate in the database
	 * @return GiftCertificate with this id from the database
	 * @throws GiftCertificateNotFoundException when GiftCertificate with this id is not stored in the database
	 * @throws DataInputException               when id is null
	 */
	GiftCertificate findById(Long id) throws GiftCertificateNotFoundException, DataInputException;

	/**
	 * @param name is for searching stored GiftCertificate in the database
	 * @return list of GiftCertificates with this name from the database
	 * @throws DataInputException when name is null
	 */
	List<GiftCertificate> findByPartOfName(String name) throws DataInputException;

	/**
	 * @param description is for searching stored GiftCertificate in the database
	 * @return list of GiftCertificates with this description from the database
	 * @throws DataInputException when description is null
	 */
	List<GiftCertificate> findByPartOfDescription(String description) throws DataInputException;

	/**
	 * @param giftCertificate is to be updated in the database
	 * @throws GiftCertificateNotFoundException when GiftCertificate is not stored in the database
	 * @throws DataInputException               when GiftCertificate is null
	 */
	void update(GiftCertificate giftCertificate) throws GiftCertificateNotFoundException, DataInputException;

	/**
	 * @param giftCertificate is to deleted from the database
	 * @throws GiftCertificateNotFoundException when GiftCertificate is not stored in the database
	 * @throws DataInputException               when GiftCertificate is null
	 */
	void delete(GiftCertificate giftCertificate) throws GiftCertificateNotFoundException, DataInputException;

	/**
	 * @param id is an id of GiftCertificate which is to be deleted from the database
	 * @throws GiftCertificateNotFoundException when GiftCertificate with this id is not stored in the database
	 * @throws DataInputException               when GiftCertificate is null
	 */
	void deleteById(Long id) throws GiftCertificateNotFoundException, DataInputException;

}
