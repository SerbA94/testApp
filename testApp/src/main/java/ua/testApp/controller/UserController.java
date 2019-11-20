package ua.testApp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import ua.testApp.model.User;
import ua.testApp.repository.UserRepository;



@Controller
public class UserController {

	private final UserRepository userRepository;

	@Autowired
	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@GetMapping("/registration")
	public String registration(Model model) {
		if (isCurrentAuthenticationAnonymous()) {
			model.addAttribute("user", new User());
			return "registration";
		}
		return "redirect:/";
	}

	@PreAuthorize("isAnonymous()")
	@PostMapping("/registration")
	public String addUser(User user, BindingResult result, Model model) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		if (result.hasErrors()) {
			return "registration";
		}
		if (userRepository.findByUsername(user.getUsername()) != null) {
			FieldError existError = new FieldError("user", "username", "User Exists!");
			result.addError(existError);
			return "registration";
		}
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		return "redirect:/login";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/profile")
	public String profile(Model model) {
		Object principal = getCurrentAuthentication().getPrincipal();
		Long id;
		User user;
		if (principal instanceof User) {
			id = ((User) principal).getId();
			user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
			model.addAttribute("user", user);
			return "user-profile";
		}
			return "redirect:/";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/update-profile")
	public String updateProfile(User user, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "user-profile";
		}
		userRepository.save(user);
		return "redirect:/profile";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete")
	public String deleteUser( Model model) {
		Object principal = getCurrentAuthentication().getPrincipal();
		Long id;
		User user;
		if (principal instanceof User) {
			id = ((User) principal).getId();
			user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
			userRepository.delete(user);
			return "redirect:/logout";
		}
			return "redirect:/";
	}
	
	private Authentication getCurrentAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	private boolean isCurrentAuthenticationAnonymous() {
		return getCurrentAuthentication().getPrincipal().equals("anonymousUser");
	}
	
}
