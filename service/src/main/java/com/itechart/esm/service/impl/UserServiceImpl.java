package com.itechart.esm.service.impl;

import com.itechart.esm.common.model.entity.User;
import com.itechart.esm.repository.UserRepository;
import com.itechart.esm.service.UserService;
import com.itechart.esm.service.exception.DataInputException;
import com.itechart.esm.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User save(User user) throws DataInputException {
		if (user == null) {
			throw new DataInputException();
		}
		return userRepository.save(user);
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public User findById(Long id) throws UserNotFoundException, DataInputException {
		if (id == null) {
			throw new DataInputException();
		}
		Optional<User> optionalUser = userRepository.findById(id);
		if (optionalUser.isEmpty()) {
			throw new UserNotFoundException();
		}
		return optionalUser.get();
	}

	@Override
	public User findByLogin(String login) throws UserNotFoundException, DataInputException {
		if (login == null || login.isEmpty()) {
			throw new DataInputException();
		}
		Optional<User> optionalUser = userRepository.findByLogin(login);
		if (optionalUser.isEmpty()) {
			throw new UserNotFoundException();
		}
		return optionalUser.get();
	}

	@Override
	public boolean isLoginBusy(String login) throws DataInputException {
		if (login == null || login.isEmpty()) {
			throw new DataInputException();
		}
		return userRepository.findByLogin(login)
				.isPresent();
	}

	@Override
	public boolean update(User user) throws DataInputException, UserNotFoundException {
		if (user == null || user.getId() == null) {
			throw new DataInputException();
		}
		Optional<User> optionalUser = userRepository.findById(user.getId());
		if (optionalUser.isEmpty()) {
			throw new UserNotFoundException();
		}
		return userRepository.update(user);
	}

	@Override
	public boolean delete(User user) throws UserNotFoundException, DataInputException {
		if (user == null || user.getId() == null) {
			throw new DataInputException();
		}
		Optional<User> optionalUser = userRepository.findById(user.getId());
		if (optionalUser.isEmpty()) {
			throw new UserNotFoundException();
		}
		return userRepository.delete(user);
	}

	@Override
	public boolean deleteById(Long id) throws DataInputException, UserNotFoundException {
		if (id == null) {
			throw new DataInputException();
		}
		Optional<User> optionalUser = userRepository.findById(id);
		if (optionalUser.isEmpty()) {
			throw new UserNotFoundException();
		}
		return userRepository.deleteById(id);
	}
}
