package com.starter.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.starter.security.AuthoritiesConstants;
import com.starter.web.filter.AuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {


	private final AuthenticationFilter authenticationFilter;
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http
		.cors(withDefaults())
		.csrf(csrf -> csrf.disable())
		.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.authorizeHttpRequests((requests) -> requests
				.requestMatchers(HttpMethod.OPTIONS ,"/**").permitAll()
				.requestMatchers(HttpMethod.POST,"/api/authenticate").permitAll()
				.requestMatchers(HttpMethod.POST,"/api/register").permitAll()
				.requestMatchers("/api/activate").permitAll()
				.requestMatchers("/api/account/reset-password/init").permitAll()
				.requestMatchers("/api/account/reset-password/finish").permitAll()
				.requestMatchers("/api/admin/**").hasAuthority("admin")
				.requestMatchers("/swagger-ui/**","/api-docs/**").hasAuthority(AuthoritiesConstants.ADMIN)
				.requestMatchers( HttpMethod.GET,"/api/products/**").permitAll()
				.requestMatchers("/api/**").authenticated()
				.anyRequest().permitAll())
		;
		
		http.addFilterBefore(authenticationFilter,UsernamePasswordAuthenticationFilter.class);
//		http.exceptionHandling(exceptions ->
//         exceptions
//             .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
//             .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
//		 );
		return http.build();
	}


	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	




}
