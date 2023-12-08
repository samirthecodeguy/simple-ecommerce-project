package com.starter.web.rest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.starter.domain.Product;
import com.starter.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductResource {
	
	private final ProductRepository productRepository;

	@GetMapping
	public Page<Product> getProducts(Pageable pageable) {
		return productRepository.findAll(pageable);
	}

}
