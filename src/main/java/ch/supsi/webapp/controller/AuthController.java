package ch.supsi.webapp.controller;

import ch.supsi.webapp.model.Role;
import ch.supsi.webapp.model.User;
import ch.supsi.webapp.service.PasswordService;
import ch.supsi.webapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
public class AuthController {
	private final UserService userService;
	private final PasswordService passwordService;

	@Autowired
	public AuthController(UserService userService, PasswordService passwordService) {
		this.userService = userService;
		this.passwordService = passwordService;
	}

	@GetMapping("/login")
	public String getLogin() {
		return "login";
	}

	@GetMapping("/register")
	public String getRegister(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}

	@PostMapping("/register")
	public String PostRegister(HttpServletRequest request, User user) {
		String plainPass = user.getPassword();
		user.setPassword(passwordService.crypt(user.getPassword()));
		user.setRole(new Role("user"));
		userService.save(user);
		try {
			request.login(user.getUsername(), plainPass);
		} catch (ServletException e) {
			e.printStackTrace();
		}
		return "redirect:/";
	}

}
