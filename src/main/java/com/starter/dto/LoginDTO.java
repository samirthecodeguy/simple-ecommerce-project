package com.starter.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LoginDTO (
		@NotNull @Size(min = 3, max = 50) String username, 
		@NotNull @Size(min = 3, max = 50) String password,
		boolean rememberMe
		) {}
