package com.starter.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.starter.domain.Authority;


public interface AuthorityRepository extends JpaRepository<Authority, String> {
	
	Optional<Authority> findByName(String name);
}
