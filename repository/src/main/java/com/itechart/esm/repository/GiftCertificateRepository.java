package com.itechart.esm.repository;

import com.itechart.esm.repository.entity.GiftCertificate;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateRepository {

	/**
	 * @param giftCertificate is an entity to be saved
	 * @return giftCertificate with generated id
	 */
	GiftCertificate save(GiftCertificate giftCertificate);

	/**
	 * @return list of giftCertificates stored in the database
	 */
	List<GiftCertificate> findAll();

	/**
	 * @param id is an id of giftCertificate stored in database
	 * @return Optional of GiftCertificate if it is stored in the database else return Empty Optional
	 */
	Optional<GiftCertificate> findById(Long id);

	/**
	 * @param name is a field in GiftCertificate objects
	 * @return list of GiftCertificates stored with this name in the database
	 */
	List<GiftCertificate> findByPartOfName(String name);

	/**
	 * @param description is a field in GiftCertificate object
	 * @return list of GiftCertificates stored with this description in the database
	 */
	List<GiftCertificate> findByPartOfDescription(String description);

	/**
	 * @param giftCertificate is to be updated in the database
	 * @return true if this giftCertificate is presented in the database else return false
	 */
	boolean update(GiftCertificate giftCertificate);

	/**
	 * @param giftCertificate is to be deleted from the database
	 * @return true if this giftCertificate is presented in the database else return false
	 */
	boolean delete(GiftCertificate giftCertificate);

	/**
	 * +
	 *
	 * @param id of giftCertificate with is to be deleted
	 * @return true if this id is presented in the database else return false
	 */
	boolean deleteById(Long id);

	/**
	 * deletes all stored GiftCertificates from the database
	 */
	void deleteAll();

}
