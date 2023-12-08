package com.starter.domain;

import java.math.BigDecimal;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product extends AbstractAuditingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "barcode", nullable = true)
	@NotNull
	private String barcode;

	@Column(name = "name", nullable = false)
	@NotNull
	private String name;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] picture;

	@Column(name = "description", nullable = false, length = 512)
	@NotNull
	private String description;

	@Column(nullable = false)
	private BigDecimal price;

	@Column(nullable = false)
	private BigDecimal costPrice;

	@Column(nullable = false)
	private Integer stockQuantity;

	@Column(nullable = false)
	private Integer lowStockThreshold;

	@ManyToOne
	@JoinColumn(name = "category", nullable = true)
	private Category category;

}
