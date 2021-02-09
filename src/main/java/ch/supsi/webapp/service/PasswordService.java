package ch.supsi.webapp.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {
	private final BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();

	public String crypt(String password) {
		return bCrypt.encode(password);
	}
}
