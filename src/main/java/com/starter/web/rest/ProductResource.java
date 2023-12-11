package com.starter.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.starter.dto.AdminProductDTO;
import com.starter.dto.ProductDTO;
import com.starter.repository.ProductRepository;
import com.starter.service.ProductService;
import com.starter.utils.PaginationUtils;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class ProductResource {

	private final ProductRepository productRepository;
	private final ProductService productService;

	@GetMapping("/products")
	public ResponseEntity<List<ProductDTO>> getAllProducts(
			@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
		log.debug("REST request to get a page of Products");
		Page<ProductDTO> page = productService.getAllProductsPageable(pageable);
		HttpHeaders headers = PaginationUtils
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	@GetMapping("/products/{id}")
	public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id, Authentication authentication) {

		log.debug("REST request to get Product : {}", id);

		ProductDTO productById = productService.getProductById(id);

		return ResponseEntity.ok().body(productById);

	}
	
	@PostMapping("/products")
	public ResponseEntity<AdminProductDTO> createProduct(@Valid @RequestBody AdminProductDTO productDTO)
			throws URISyntaxException {
		
		log.debug("REST request to create Product : {}", productDTO);
		
		AdminProductDTO dto = productService.createProduct(productDTO);
		
		return ResponseEntity
				.created(new URI("/api/products/" + dto.getId()))
				.body(dto);
	}
	
	@PutMapping("/products/{id}")
	public ResponseEntity<AdminProductDTO> updateProduct(@PathVariable long id ,@Valid @RequestBody AdminProductDTO productDTO) 
			throws URISyntaxException {
		
		if (Long.compare(id, productDTO.getId()) != 0) {
			throw new ErrorResponseException(HttpStatus.BAD_REQUEST);
		}
		
		if (!productRepository.existsById(id)) {
			throw new IllegalArgumentException("product with id" + id + "not found"); 
		}
		
		log.debug("REST request to update Product : {}", productDTO);
		
		AdminProductDTO dto = productService.updateProduct(productDTO);
		
		return ResponseEntity
				.created(new URI("/api/products/" + dto.getId()))
				.body(dto);
	}
	
	@DeleteMapping("/products/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
		
		log.debug("REST request to delete Product : {}", id);
		
		productService.deleteProduct(id);
		
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	

}
