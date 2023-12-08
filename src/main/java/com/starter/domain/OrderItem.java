package com.starter.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_item_id")
	private Long orderItemId;

	@ManyToOne
	@JoinColumn(name = "order_id", nullable = false)
	@NotNull
	private Order order;

	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	@NotNull
	private Product product;

	@Column(name = "quantity", nullable = false)
	@Min(value = 1)
	private int quantity;

	@Column(name = "unit_price", nullable = false)
	private double unitPrice;

	@Column(name = "subtotal", nullable = false)
	private double subtotal;

	// Other relevant order item information, getters, and setters
}
