package com.starter.repository;

import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.starter.domain.User;


public interface UserRepository extends JpaRepository<User, Long> {
	
	String USERS_BY_USERNAME_CACHE = "usersByUsername";

	@Cacheable(cacheNames=USERS_BY_USERNAME_CACHE)
	Optional<User> findOneWithAuthoritiesByUsername(String login);
	
	Optional<User> findByUsername(String username);
}
