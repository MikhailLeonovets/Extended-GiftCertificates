package com.itechart.esm.service;

import com.itechart.esm.repository.entity.Tag;
import com.itechart.esm.service.exception.DataInputException;
import com.itechart.esm.service.exception.GiftCertificateNotFoundException;
import com.itechart.esm.service.exception.GiftCertificateTagNotFoundException;
import com.itechart.esm.service.exception.TagNotFoundException;
import com.itechart.esm.service.model.GiftCertificateAndItsTags;

import java.util.List;

/**
 * This class is for combining Tags and GiftCertificates into GiftCertificateAndItsTags which present to the client
 * all information of the GiftCertificate
 */
public interface GiftCertificateManagementService {

	/**
	 * @param giftCertificateAndItsTags creates this giftCertificateAndItsTags and saves it in the database
	 * @throws DataInputException when giftCertificateAndItsTags is null
	 */
	void createGiftCertificate(GiftCertificateAndItsTags giftCertificateAndItsTags) throws DataInputException;

	/**
	 * @return lift of all GiftCertificateAndItsTags stored in the database
	 * @throws DataInputException               when something goes wrong with data in the database
	 * @throws TagNotFoundException             when something goes wrong with data in the database
	 * @throws GiftCertificateNotFoundException when something goes wrong with data in the database
	 */
	List<GiftCertificateAndItsTags> findAll() throws DataInputException, TagNotFoundException,
			GiftCertificateNotFoundException;

	/**
	 * @return list of all GiftCertificateAndItsTags stored in the database sorted by GiftCertificate field
	 * DateOfCreation
	 * @throws TagNotFoundException             when something goes wrong with data in the database
	 * @throws GiftCertificateNotFoundException when something goes wrong with data in the database
	 * @throws DataInputException               when something goes wrong with data in the database
	 */
	List<GiftCertificateAndItsTags> findAllSortByDateOfCreation()
			throws TagNotFoundException, GiftCertificateNotFoundException, DataInputException;

	/**
	 * @return list of all GiftCertificateAndItsTags stored in the database sorted by GiftCertificate field name
	 * @throws TagNotFoundException             when something goes wrong with data in the database
	 * @throws GiftCertificateNotFoundException when something goes wrong with data in the database
	 * @throws DataInputException               when something goes wrong with data in the database
	 */
	List<GiftCertificateAndItsTags> findAllSortByName()
			throws TagNotFoundException, GiftCertificateNotFoundException, DataInputException;

	/**
	 * @param id of GiftCertificate is for searching GiftCertificateAndItsTags
	 * @return GiftCertificateAndItsTags which contains GiftCertificate with this id
	 * @throws GiftCertificateNotFoundException when something goes wrong with data in the database
	 * @throws DataInputException               when GiftCertificate with this id is not found
	 * @throws TagNotFoundException             when something goes wrong with data in the database
	 */
	GiftCertificateAndItsTags findByGiftCertificateId(Long id) throws GiftCertificateNotFoundException, DataInputException,
			TagNotFoundException;

	/**
	 * @param tag is for searching all GiftCertificateAndItsTags with this it
	 * @return list of GiftCertificateAndItsTags which stored in the database with this tag
	 * @throws DataInputException               when something goes wrong with data in the database or tag is null
	 * @throws TagNotFoundException             when this tag or any adjacent tag is not found in the database
	 * @throws GiftCertificateNotFoundException when something goes wrong with data in the database
	 */
	List<GiftCertificateAndItsTags> findByTag(Tag tag) throws DataInputException, TagNotFoundException,
			GiftCertificateNotFoundException;

	/**
	 * @param tagName is for searching all GiftCertificateAndItsTags with tag with this name
	 * @return list of GiftCertificateAndItsTags which stored in the database with this tag which is with this name
	 * @throws DataInputException               when something goes wrong with data in the database or tagName is null
	 * @throws TagNotFoundException             when something goes wrong with data in the database
	 * @throws GiftCertificateNotFoundException when something goes wrong with data in the database
	 */
	List<GiftCertificateAndItsTags> findByTag(String tagName) throws DataInputException, TagNotFoundException,
			GiftCertificateNotFoundException;

	/**
	 * @param tagId is for searching all GiftCertificateAndItsTags with tag with this id
	 * @return list of GiftCertificateAndItsTags which stored in the database with this tag which is with this id
	 * @throws DataInputException               when something goes wrong with data in the database or tagId is null
	 * @throws TagNotFoundException             when something goes wrong with data in the database
	 * @throws GiftCertificateNotFoundException when something goes wrong with data in the database
	 */
	List<GiftCertificateAndItsTags> findByTag(Long tagId) throws DataInputException, TagNotFoundException,
			GiftCertificateNotFoundException;

	/**
	 * @param name is for searching all GiftCertificateAndItsTags with giftCertificate which contains part of this in
	 *             its name
	 * @return list of GiftCertificateAndItsTags which stored in the database with GiftCertificate which contains the
	 * name with this part of the name
	 * @throws DataInputException               when something goes wrong with data in the database or name is null
	 * @throws TagNotFoundException             when something goes wrong with data in the database
	 * @throws GiftCertificateNotFoundException when something goes wrong with data in the database
	 */
	List<GiftCertificateAndItsTags> findByPartOfName(String name)
			throws DataInputException, TagNotFoundException, GiftCertificateNotFoundException;

	/**
	 * @param description is for searching all GiftCertificateAndItsTags with giftCertificate which contains part of
	 *                    this in its description
	 * @return list of GiftCertificateAndItsTags which stored in the database with GiftCertificate which contains the
	 * name with this part of the description
	 * @throws DataInputException               when something goes wrong with data in the database or description is null
	 * @throws TagNotFoundException             when something goes wrong with data in the database
	 * @throws GiftCertificateNotFoundException when something goes wrong with data in the database
	 */
	List<GiftCertificateAndItsTags> findByPartOfDescription(String description)
			throws DataInputException, TagNotFoundException, GiftCertificateNotFoundException;

	/**
	 * @param giftCertificateAndItsTags is to be updated in the database with updating giftCertificate and tags
	 * @throws GiftCertificateNotFoundException    when something goes wrong with data in the database
	 * @throws DataInputException                  when something goes wrong with data in the database or giftCertificateAndItsTag is
	 *                                             null
	 * @throws TagNotFoundException                when something goes wrong with data in the database
	 * @throws GiftCertificateTagNotFoundException when something goes wrong with data in the database
	 */
	void update(GiftCertificateAndItsTags giftCertificateAndItsTags) throws GiftCertificateNotFoundException,
			DataInputException, TagNotFoundException, GiftCertificateTagNotFoundException;

	/**
	 * @param giftCertificateAndItsTags is for to be deleted from the database. It deletes only giftCertificate and
	 *                                  giftCertificateTag. Tags are not to be deleted from the database
	 * @throws GiftCertificateNotFoundException when something goes wrong with data in the database
	 * @throws DataInputException               when something goes wrong with data in the database or giftCertificateAndItsTags is
	 *                                          null
	 */
	void delete(GiftCertificateAndItsTags giftCertificateAndItsTags) throws GiftCertificateNotFoundException,
			DataInputException;

	/**
	 * @param id is an id of giftCertificate to be deleted from the database. It deletes only giftCertificate and
	 *           giftCertificateTag. Tags are not to be deleted from the database
	 * @throws GiftCertificateNotFoundException when something goes wrong with data in the database
	 * @throws DataInputException               when something goes wrong with data in the database or id is null
	 */
	void deleteById(Long id) throws GiftCertificateNotFoundException, DataInputException;

}
