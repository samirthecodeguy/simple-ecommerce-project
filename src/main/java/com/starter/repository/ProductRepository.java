package com.starter.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.starter.domain.Category;
import com.starter.domain.Product;



public interface ProductRepository extends JpaRepository<Product, Long> {
	
	Optional<Product> findByName(String name);
	
	List<Product> findByCategory(Category category);

	Page<Product> findByCategoryName( Pageable pageable, String category);
}
