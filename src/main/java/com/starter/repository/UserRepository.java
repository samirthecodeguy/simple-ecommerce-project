package com.starter.repository;

import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.starter.domain.User;


public interface UserRepository extends JpaRepository<User, Long> {
	
	@Cacheable(value="usersByUsername", key="#usrnmauth")
	Optional<User> findOneWithAuthoritiesByUsername(String login);
	
	Optional<User> findByUsername(String username);
}
