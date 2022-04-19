package com.itechart.esm.service;

import com.itechart.esm.repository.entity.GiftCertificate;
import com.itechart.esm.repository.entity.GiftCertificateTag;
import com.itechart.esm.repository.entity.Tag;
import com.itechart.esm.service.exception.DataInputException;
import com.itechart.esm.service.exception.GiftCertificateNotFoundException;
import com.itechart.esm.service.exception.GiftCertificateTagNotFoundException;
import com.itechart.esm.service.exception.TagNotFoundException;

import java.util.List;

public interface GiftCertificateTagService {

	/**
	 * @param giftCertificateTag is to be saved in the database
	 * @throws DataInputException when giftCertificateTag is null
	 */
	void save(GiftCertificateTag giftCertificateTag) throws DataInputException;

	/**
	 * @return list of giftCertificateTags stored in the database
	 * @throws GiftCertificateTagNotFoundException if there are no giftCertificateTags in the database
	 * @throws DataInputException                  when something goes wrong in the database
	 * @throws GiftCertificateNotFoundException    when data stored in the database is wrong
	 * @throws TagNotFoundException                when data stored in the database is wrong
	 */
	List<GiftCertificateTag> findAll() throws GiftCertificateTagNotFoundException, DataInputException,
			GiftCertificateNotFoundException, TagNotFoundException;

	/**
	 * @param id is for searching giftCertificateTag in the database
	 * @return giftCertificateTag with this id
	 * @throws GiftCertificateTagNotFoundException when there is no giftCertificateTag with this id
	 * @throws DataInputException                  when something goes wrong in the database
	 * @throws GiftCertificateNotFoundException    when data stored in the database is wrong
	 * @throws TagNotFoundException                when data stored in the database is wrong
	 */
	GiftCertificateTag findById(Long id) throws GiftCertificateTagNotFoundException, DataInputException,
			GiftCertificateNotFoundException, TagNotFoundException;

	/**
	 * @param tag is for searching giftCertificateTags in the database
	 * @return list of giftCertificateTags with this tag
	 * @throws DataInputException               when something goes wrong in the database
	 * @throws GiftCertificateNotFoundException when data stored in the database is wrong
	 * @throws TagNotFoundException             when data stored in the database is wrong
	 */
	List<GiftCertificateTag> findByTag(Tag tag) throws DataInputException, GiftCertificateNotFoundException,
			TagNotFoundException;

	/**
	 * @param giftCertificate is for searching giftCertificateTags in the database
	 * @return list of giftCertificateTags with this giftCertificate
	 * @throws DataInputException               when something goes wrong in the database
	 * @throws GiftCertificateNotFoundException when data stored in the database is wrong
	 * @throws TagNotFoundException             when data stored in the database is wrong
	 */
	List<GiftCertificateTag> findByGiftCertificate(GiftCertificate giftCertificate) throws DataInputException,
			GiftCertificateNotFoundException, TagNotFoundException;

	/**
	 * @param tagId             is for searching giftCertificateTags in the database
	 * @param giftCertificateId is for searching giftCertificateTags in the database
	 * @return list of giftCertificateTags with these tag and giftCertificate
	 * @throws GiftCertificateTagNotFoundException when there is no giftCertificateTag with these tagId and
	 *                                             giftCertificateId
	 * @throws GiftCertificateNotFoundException    when data stored in the database is wrong
	 * @throws TagNotFoundException                when data stored in the database is wrong
	 * @throws DataInputException                  when something goes wrong in the database
	 */
	GiftCertificateTag findByTagIdAndGiftCertificateId(Long tagId, Long giftCertificateId)
			throws GiftCertificateTagNotFoundException, GiftCertificateNotFoundException, TagNotFoundException, DataInputException;

	/**
	 * @param giftCertificateTag is to be deleted from the database
	 * @throws GiftCertificateTagNotFoundException when there is no such giftCertificateTag stored in the database
	 * @throws DataInputException                  when giftCertificateTag is null
	 */
	void delete(GiftCertificateTag giftCertificateTag) throws GiftCertificateTagNotFoundException, DataInputException;

	/**
	 * @param id is an id of giftCertificateTag which is to be deleted from the database
	 * @throws GiftCertificateTagNotFoundException when there is no such giftCertificateTag with this id stored in the
	 *                                             database
	 * @throws DataInputException                  when id is null
	 */
	void deleteById(Long id) throws GiftCertificateTagNotFoundException, DataInputException;

}
