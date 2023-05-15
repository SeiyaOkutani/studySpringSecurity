package com.study;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final SiteUserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println(username + "の認証を開始します");
		var user = repository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
		System.out.println(user.getUsername() + "の認証はOKです");

		return new User(user.getUsername(), user.getPassword(),
				AuthorityUtils.createAuthorityList(user.getAuthority().name())); // ユーザ情報を返します
	}
}
