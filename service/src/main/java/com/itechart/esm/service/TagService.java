package com.itechart.esm.service;

import com.itechart.esm.repository.entity.Tag;
import com.itechart.esm.service.exception.DataInputException;
import com.itechart.esm.service.exception.TagNotFoundException;

import java.util.List;

public interface TagService {

	/**
	 *
	 * @param tag is to be saved to the database
	 * @return tag with generated id
	 * @throws DataInputException when tag is null
	 */
	Tag save(Tag tag) throws DataInputException;

	/**
	 *
	 * @return list of all stored tags in the database
	 */
	List<Tag> findAll();

	/**
	 *
	 * @param id is for searching in the database tag with this id
	 * @return tag with this id
	 * @throws TagNotFoundException when the tag with this id is not stored in the database
	 * @throws DataInputException when id is null
	 */
	Tag findById(Long id) throws TagNotFoundException, DataInputException;

	/**
	 *
	 * @param name is for searching in the database tag with this name
	 * @return Tag with this name
	 * @throws TagNotFoundException when the tag with this name is not stored in the database
	 * @throws DataInputException when name is null or empty
	 */
	Tag findByName(String name) throws TagNotFoundException, DataInputException;

	/**
	 *
	 * @param tag is to be deleted from the database
	 * @throws TagNotFoundException when tag is not presented in the database
	 * @throws DataInputException when tag is null
	 */
	void delete(Tag tag) throws TagNotFoundException, DataInputException;


	/**
	 *
	 * @param id is an id of the tag which is to be deleted from the database
	 * @throws TagNotFoundException when tag with this id is not presented in the database
	 * @throws DataInputException when id is null
	 */
	void deleteById(Long id) throws TagNotFoundException, DataInputException;

}
