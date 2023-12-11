package com.starter.dto;

import java.math.BigDecimal;

import com.starter.domain.Product;

import jakarta.annotation.Nullable;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
//@JsonSerialize(using = ProductDTOSerializer.class)
public class ProductDTO {
	
	private long id;
	
	private String barcode;
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String description;
	
	@NotNull
	private BigDecimal price;
	
	private int stockQuantity;
	
	@Lob
	private byte[] picture;
	
	@NotNull
	private String categoryName;

	public ProductDTO(Product product) {
		this.id = product.getId();
		this.barcode = product.getBarcode();
		this.name = product.getName();
		this.description = product.getDescription();
		this.price = product.getPrice();
		this.stockQuantity = product.getStockQuantity();
		this.picture = product.getPicture();
		this.categoryName = product.getCategory().getName();
	}

}
