package com.starter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.starter.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
