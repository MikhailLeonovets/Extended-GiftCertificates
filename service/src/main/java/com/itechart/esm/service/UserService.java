package com.itechart.esm.service;

import com.itechart.esm.common.model.entity.User;
import com.itechart.esm.service.exception.DataInputException;
import com.itechart.esm.service.exception.UserNotFoundException;

import java.util.List;

public interface UserService {

	User save(User user) throws DataInputException;

	List<User> findAll();

	User findById(Long id) throws UserNotFoundException, DataInputException;

	User findByLogin(String login) throws UserNotFoundException, DataInputException;

	boolean isLoginBusy(String login) throws DataInputException;

	boolean update(User user) throws DataInputException, UserNotFoundException;

	boolean delete(User user) throws UserNotFoundException, DataInputException;

	boolean deleteById(Long id) throws DataInputException, UserNotFoundException;

}
