package ch.supsi.webapp.service;

import ch.supsi.webapp.model.User;
import ch.supsi.webapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	private final UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public Iterable<User> getAll() {
		return userRepository.findAll();
	}

	public User findUserByUsername(String username) {
		return userRepository.findUserByUsername(username);
	}

	public User getLoggedUser() {
		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userRepository.findUserByUsername(user.getUsername());
	}

	public User save(User user) {
		return userRepository.save(user);
	}
}
