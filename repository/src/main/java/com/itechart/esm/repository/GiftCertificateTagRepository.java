package com.itechart.esm.repository;

import com.itechart.esm.repository.entity.GiftCertificate;
import com.itechart.esm.repository.entity.GiftCertificateTag;
import com.itechart.esm.repository.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateTagRepository {

	/**
	 * @param giftCertificateTag is to be saved to the database
	 * @return this giftCertificateTag with generated id
	 */
	GiftCertificateTag save(GiftCertificateTag giftCertificateTag);

	/**
	 * @return list of all stored giftCertificateTags from the database
	 */
	List<GiftCertificateTag> findAll();

	/**
	 * @param id is an id of giftCertificateTag stored in database
	 * @return Optional of giftCertificateTag if it is stored in the database else return Empty Optional
	 */
	Optional<GiftCertificateTag> findById(Long id);

	/**
	 * @param tag is the field of giftCertificateTags to search in the database
	 * @return list of giftCertificateTags which contains this tag field
	 */
	List<GiftCertificateTag> findByTag(Tag tag);

	/**
	 * @param giftCertificate is the field of giftCertificateTags to search in the database
	 * @return list of giftCertificateTags which contains this giftCertificate field
	 */
	List<GiftCertificateTag> findByGiftCertificate(GiftCertificate giftCertificate);


	/**
	 * @param tagId             is the id of field of giftCertificateTags to search in the database
	 * @param giftCertificateId is the field of giftCertificateTags to search in the database
	 * @return giftCertificateTag list of giftCertificateTags which contains this tag id field and giftCertificateId
	 * field
	 */
	Optional<GiftCertificateTag> findByTagIdAndGiftCertificateId(Long tagId, Long giftCertificateId);

	/**
	 * @param giftCertificateTag to be deleted from the database
	 * @return true if this giftCertificateTag is presented in the database else return false
	 */
	boolean delete(GiftCertificateTag giftCertificateTag);

	/**
	 * @param id giftCertificateTag of to be deleted from the database
	 * @return true if this giftCertificateTag is presented in the database else return false
	 */
	boolean deleteById(Long id);

}
