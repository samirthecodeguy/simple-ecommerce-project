package com.starter.service.impl;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class JwtService {

	private static final String PREFIX = "Bearer ";

	private static final String AUTHORITIES_KEY = "auth";

	@Value("${jwt.token-validity-in-seconds:0}")
	private long tokenValidityInSeconds;

	@Value("${jwt.token-validity-in-seconds-for-remember-me:0}")
	private long tokenValidityInSecondsForRememberMe;

	@Value("${jwt.secret}")
	private String base64Key;

	public String generateToken(Authentication authentication, boolean rememberMe) {

		String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));

		Instant now = Instant.now();
		Instant validity;

		if (rememberMe)
			validity = now.plus(this.tokenValidityInSecondsForRememberMe, ChronoUnit.SECONDS);
		else
			validity = now.plus(this.tokenValidityInSeconds, ChronoUnit.SECONDS);

		return Jwts.builder().subject(authentication.getName()).claim(AUTHORITIES_KEY, authorities)
				.expiration(new Date(System.currentTimeMillis() + validity.toEpochMilli())).signWith(getSecretKey())
				.compact();

	}

	public UserDetails getAuthUser(HttpServletRequest request) {
		
		String token = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (token != null) {
			Claims claims = Jwts.parser().verifyWith(getSecretKey()).build()
					.parseSignedClaims(token.replace(PREFIX, "")).getPayload();

			String username = claims.getSubject();

			List<String> authoritiesList = Arrays.asList(claims.get(AUTHORITIES_KEY).toString().split(","));
			List<SimpleGrantedAuthority> authorities = authoritiesList.stream().map(SimpleGrantedAuthority::new)
					.collect(Collectors.toList());

			return new org.springframework.security.core.userdetails.User(username, "", authorities);
		}
		return null;
	}

	private SecretKey getSecretKey() {
		try {

			byte[] keyBytes = Base64.getDecoder().decode(base64Key);

			SecretKey secretKey = Keys.hmacShaKeyFor(keyBytes);

			return secretKey;
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}
	}
}
