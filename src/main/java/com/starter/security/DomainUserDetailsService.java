package com.starter.security;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.starter.domain.Authority;
import com.starter.domain.User;
import com.starter.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class DomainUserDetailsService implements UserDetailsService {
	
	private final UserRepository userRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		log.debug("Authenticating {}", username);

		String lowerCaseUsername = username.toLowerCase();

		return userRepository.findOneWithAuthoritiesByUsername(lowerCaseUsername)
				.map(user -> createSpringSecurityUser(lowerCaseUsername, user))
				.orElseThrow(() -> new UsernameNotFoundException(
						"User " + lowerCaseUsername + " was not found"));
	}

	private org.springframework.security.core.userdetails.User createSpringSecurityUser(String lowercaseUsername,
			User user) throws UserNotActivatedException {

		if (!user.isActivated()) {
			throw new UserNotActivatedException("User " + lowercaseUsername + " was not activated");
		}
		
		List<SimpleGrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
				.map(Authority::getName)
				.map(SimpleGrantedAuthority::new)
				.toList();
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				grantedAuthorities);
	}
}
