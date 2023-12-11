package com.starter.dto;

import java.math.BigDecimal;

import com.starter.domain.Product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class AdminProductDTO extends ProductDTO {

	private int lowStockThreshold;
	
	@NotNull
	private BigDecimal costPrice;

	public AdminProductDTO(Product product) {
		super(product);
		this.costPrice = product.getCostPrice();
		this.lowStockThreshold = product.getLowStockThreshold();
	}

}