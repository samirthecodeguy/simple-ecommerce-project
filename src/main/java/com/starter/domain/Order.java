package com.starter.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customer_order")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Order extends AbstractAuditingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private Long orderId;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(name = "order_date", nullable = false)
	private LocalDateTime orderDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private OrderStatus status;

	@Column(name = "total_amount", nullable = false)
	private double totalAmount;

	@Column(name = "shipping_address")
	private String shippingAddress;

	@Column(name = "transaction_id")
	private String transactionId;

	@Column(name = "delivered_date")
	private LocalDateTime deliveredDate;

	@Column(name = "canceled_date")
	private LocalDateTime canceledDate;

	public enum OrderStatus {
		PROCESSING, SHIPPED, DELIVERED, CANCELED
	}

//	@Column(name = "payment_method")
//	private String paymentMethod;

}
