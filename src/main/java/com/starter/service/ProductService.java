package com.starter.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.starter.dto.AdminProductDTO;
import com.starter.dto.ProductDTO;

public interface ProductService {
	
	ProductDTO getProductById(Long id);
	
	Page<ProductDTO> findProductsByCategory(Pageable pageable, String category);

	Page<ProductDTO> getAllProductsPageable(Pageable pageable);
	
	AdminProductDTO createProduct(AdminProductDTO productDTO);
	
	AdminProductDTO updateProduct(AdminProductDTO productDTO);
	
	void deleteProduct(Long id);
}
