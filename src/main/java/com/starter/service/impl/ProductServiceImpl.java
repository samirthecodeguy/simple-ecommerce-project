package com.starter.service.impl;

import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.starter.domain.Product;
import com.starter.repository.ProductRepository;
import com.starter.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;

	@Override
	@Cacheable(value = "productsByName", key = "#name")
	public Product findByName(String name) {
		//
		Optional<Product> optProduct = productRepository.findByName(name);

		return optProduct.orElseGet(null);
	}

}
