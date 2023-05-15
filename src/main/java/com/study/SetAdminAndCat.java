package com.study;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class SetAdminAndCat implements ApplicationRunner {

	private final PasswordEncoder passwordEncoder;
	private final SiteUserRepository userRepository;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		var user = new SiteUser();
		user.setUsername("admin");// "admin"ユーザを用意します
		user.setPassword(passwordEncoder.encode("padmin"));
		user.setEmail("admin@example.com");
		user.setGender(0);
		user.setDepartment(0);
		user.setAdmin(true);
		user.setAuthority(Authority.ADMIN);
		if (userRepository.findByUsername(user.getUsername()).isEmpty()) { // ユーザが存在しない場合は登録する
			userRepository.save(user);
		}

		user = new SiteUser();
		user.setUsername("cat");// 一般ユーザを用意します
		user.setPassword(passwordEncoder.encode("pcat"));
		user.setEmail("cat@example.com");
		user.setGender(0);
		user.setDepartment(0);
		user.setAdmin(false);
		user.setAuthority(Authority.USER);
		if (userRepository.findByUsername(user.getUsername()).isEmpty()) {
			userRepository.save(user);
		}
		System.out.println("adminパスワードはpadmin、catパスワードはpcat");
	}
}
