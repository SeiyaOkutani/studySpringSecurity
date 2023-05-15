package com.study;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class SecurityController {

	private final SiteUserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/OK")
	public String showList(Authentication loginUser, Model model) {
		model.addAttribute("username", loginUser.getName());
		model.addAttribute("authority", loginUser.getAuthorities());
		return "user";
	}

	@GetMapping("/admin/list")
	public String showAdminList(Model model) { // ユーザ一覧の表示
		model.addAttribute("users", userRepository.findAll());
		return "list";
	}

	@GetMapping("/register")
	public String register(@ModelAttribute("user") SiteUser user) {
		return "register";
	}

	@PostMapping("/register")
	public String process(@Validated @ModelAttribute("user") SiteUser user, BindingResult result) {
		if (result.hasErrors()) {
			return "register";
		}

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		if (user.isAdmin()) {
			user.setAuthority(Authority.ADMIN);
		} else {
			user.setAuthority(Authority.USER);
		}
		userRepository.save(user);

		return "redirect:/login?register";
	}

	@GetMapping("/admin/list/edit")
	public String edit(@RequestParam("id") Long id, Model model) {
		Optional<SiteUser> optionaSiteUser = userRepository.findById(id);
		SiteUser siteUser = optionaSiteUser.get();
		model.addAttribute("user", siteUser);

		return "edit";
	}

	@PostMapping("/admin/list/edit")
	public String process2(@Validated @ModelAttribute("user") SiteUser user, BindingResult result) {
		if (result.hasErrors()) {
			return "edit";
		}

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		if (user.isAdmin()) {
			user.setAuthority(Authority.ADMIN);
		} else {
			user.setAuthority(Authority.USER);
		}
		userRepository.save(user);

		return "redirect:/admin/list";
	}

	@GetMapping("/admin/list/delete")
	public String delete(@RequestParam("id") Long id) {
		userRepository.deleteById(id);

		return "redirect:/admin/list";
	}
}
