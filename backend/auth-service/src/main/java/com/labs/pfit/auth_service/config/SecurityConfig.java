package com.labs.pfit.auth_service.config;

import java.security.SecureRandom;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {
	
	private static final SecureRandom random = new SecureRandom();
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10, random);
	}
	
	@Bean
	public SecurityWebFilterChain basicSecurityFilterChain(ServerHttpSecurity http) {
		http
			.csrf(ServerHttpSecurity.CsrfSpec::disable)
			.httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
			.formLogin(ServerHttpSecurity.FormLoginSpec::disable)
			.authorizeExchange(
				exchanges -> exchanges
					             .pathMatchers("/auth/**").permitAll()
					             .anyExchange().authenticated()
			);
		return http.build();
	}
}