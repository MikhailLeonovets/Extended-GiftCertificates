package com.itechart.esm.repository;

import com.itechart.esm.common.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

	User save(User user);

	List<User> findAll();

	Optional<User> findById(Long id);

	Optional<User> findByLogin(String login);

	boolean update(User user);

	boolean delete(User user);

	boolean deleteById(Long id);

}
