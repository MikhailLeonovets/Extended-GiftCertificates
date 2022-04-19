package com.itechart.esm.repository;

import com.itechart.esm.repository.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagRepository {

	/**
	 * @param tag is to be saved to the database
	 * @return tag with generated id
	 */
	Tag save(Tag tag);

	/**
	 * @return list of all stored Tags in the database
	 */
	List<Tag> findAll();

	/**
	 * @param id is an id of the Tag entity to search in the database
	 * @return Optional of Tag if this id is presented in the database else return Empty Optional
	 */
	Optional<Tag> findById(Long id);

	/**
	 * +
	 *
	 * @param name is searching parameter in the column name in the database
	 * @return Optional of Tag if there is the Tag with this name in the database else return Empty Optional
	 */
	Optional<Tag> findByName(String name);

	/**
	 * @param tag is to be deleted from the database
	 * @return true if this Tag is presented in the database else return false
	 */
	boolean delete(Tag tag);

	/**
	 * @param id is an id of the Tag to be deleted from the database
	 * @return true if this id is presented in the database else return false
	 */
	boolean deleteById(Long id);

	/**
	 * deletes all Tags from the database
	 */
	void deleteAll();

}
