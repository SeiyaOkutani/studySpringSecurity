package com.study;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
// @Configurationを付ければ、メソッドに@Beanを付けれる
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { //保護するパスと、保護しないパスを定義する
		//			　仮引数(authorize) -> 処理{authorize}
		http.authorizeHttpRequests(auth -> auth
				.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() //css/**などはログイン無しでもアクセス可能
				.requestMatchers("/register", "/login").permitAll() //registerとloginは認証を必要としない
				.requestMatchers("/admin/**").hasAuthority(Authority.ADMIN.name())//admin配下は、ADMINユーザだけアクセス可能
				.anyRequest().authenticated() // 他のURLはログイン後のみアクセス可能
		)
				.formLogin(login -> login
						.loginPage("/login") // ログイン画面のURL
						.defaultSuccessUrl("/OK", true) // ログイン成功後のリダイレクト先URL
						.failureUrl("/login") // ログイン失敗後のリダイレクト先URL
						.permitAll()) // ログイン画面は未ログインでもアクセス可能
				.logout(logout -> logout
						//.logoutSuccessUrl("/")	// ログアウト成功後のリダイレクト先URL
						.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
						.permitAll());
		return http.build();
	}
}
