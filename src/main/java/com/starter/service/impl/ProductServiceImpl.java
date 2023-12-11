package com.starter.service.impl;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.starter.domain.Category;
import com.starter.domain.Product;
import com.starter.dto.AdminProductDTO;
import com.starter.dto.ProductDTO;
import com.starter.repository.CategoryRepository;
import com.starter.repository.ProductRepository;
import com.starter.security.AuthoritiesConstants;
import com.starter.security.SecurityUtils;
import com.starter.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;

	@Override
	@Transactional(readOnly = true)
	public ProductDTO getProductById(Long id) {

		Product product = productRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Product with id " + id + " not found"));

		boolean isAdmin = SecurityUtils.hasCurrentUserAnyOfAuthorities(AuthoritiesConstants.ADMIN);

		return isAdmin ? new AdminProductDTO(product) : new ProductDTO(product);
	}

	@Override
	public Page<ProductDTO> getAllProductsPageable(Pageable pageable) {

		Page<Product> products = productRepository.findAll(pageable);

		boolean isAdmin = SecurityUtils.hasCurrentUserAnyOfAuthorities(AuthoritiesConstants.ADMIN);

		return mapProductsToDTOs(products, isAdmin);
	}

	@Override
	public Page<ProductDTO> findProductsByCategory(Pageable pageable, String category) {
		throw new NotImplementedException();
	}

	@Override
	public AdminProductDTO createProduct(AdminProductDTO productDTO) {
		Product newProduct = productRepository.save(mapToEntity(productDTO));
		return new AdminProductDTO(newProduct);
	}

	@Override
	public AdminProductDTO updateProduct(AdminProductDTO productDTO) {

		Product product = mapToEntity(productDTO);

		Product updatedProduct = productRepository.save(product);

		return new AdminProductDTO(updatedProduct);

	}

	@Override
	public void deleteProduct(Long id) {
		productRepository.deleteById(id);

	}

	private Product mapToEntity(ProductDTO productDTO) {

		Product product = new Product();
		product.setBarcode(productDTO.getBarcode());
		product.setName(productDTO.getName());
		product.setPicture(productDTO.getPicture());
		product.setDescription(productDTO.getDescription());
		product.setPrice(productDTO.getPrice());
		product.setStockQuantity(productDTO.getStockQuantity());

		if (productDTO instanceof AdminProductDTO adminProductDTO) {
			product.setCostPrice(adminProductDTO.getCostPrice());
			product.setLowStockThreshold(adminProductDTO.getLowStockThreshold());
		}

		Category category = categoryRepository.findByName(productDTO.getCategoryName())
				.orElseThrow(() -> new IllegalArgumentException(
						"Category with name " + productDTO.getCategoryName() + " not found"));

		product.setCategory(category);

		return product;
	}

	private Page<ProductDTO> mapProductsToDTOs(Page<Product> products, boolean isAdmin) {
		return isAdmin ? products.map(AdminProductDTO::new) : products.map(ProductDTO::new);
	}

}
