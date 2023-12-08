package com.starter.web.rest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.starter.dto.LoginDTO;
import com.starter.service.impl.JwtService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationResource {

	private final JwtService jwtService;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;


	@GetMapping("/authenticate")
	public String authenticate() {
		return "authenticated";
	}

	@PostMapping("/authenticate")
	public ResponseEntity<?> authenticate(@RequestBody LoginDTO loginDTO) {


		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				loginDTO.username(), loginDTO.password());

		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		

		String token = jwtService.generateToken(authentication, loginDTO.rememberMe());

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);
		return ResponseEntity.ok().headers(headers).build();
	}

}
