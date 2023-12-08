package com.starter.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
	value = { "createdBy", "createdDate", "lastModifiedBy", "lastModifiedDate" }
	,allowGetters = true)
@Setter
@Getter
public abstract class AbstractAuditingEntity {
	
	@CreatedDate
	@Column(name = "created_date", updatable = false, nullable = false)
	private LocalDateTime createdDate = LocalDateTime.now();
	
	@LastModifiedDate
	private LocalDateTime lastModifiedDate;
	
	@CreatedBy
	@Column(name = "created_by", updatable = false, nullable = false)
	private String createdBy;
	
	@LastModifiedBy
	private String lastModifiedBy;
	
	
}
