package com.starter.web.filter;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.starter.service.impl.JwtService;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

	private final JwtService jwtService;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		   String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

		    if (authHeader == null) {
		        filterChain.doFilter(request, response);
		        return;
		    }
		    try {
		        UserDetails userDetails = jwtService.getAuthUser(request);
		        if (userDetails != null) {
		            Authentication authentication = new UsernamePasswordAuthenticationToken(
		                    userDetails.getUsername(), null, userDetails.getAuthorities());

		            SecurityContextHolder.getContext().setAuthentication(authentication);
		        }
		    } catch (JwtException e) {
		        // Handle JWT parsing or verification errors
		        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		        response.getWriter().write("Invalid token");
		        return;
		    }

		    filterChain.doFilter(request, response);

	}

}
