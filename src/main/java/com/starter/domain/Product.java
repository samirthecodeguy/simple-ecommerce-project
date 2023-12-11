package com.starter.domain;

import java.math.BigDecimal;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.starter.dto.AdminProductDTO;

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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Product extends AbstractAuditingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "barcode", nullable = true)
	private String barcode;

	@Column(name = "name", nullable = false)
	@NotNull
	@NotBlank
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
	private int stockQuantity;

	@Column(nullable = false)
	private int lowStockThreshold;

	@ManyToOne
	@JoinColumn(name = "category", nullable = true)
	@JsonIgnore
	private Category category;
	
}
