package com.labs.pfit.auth_service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "security.jwt")
public class JWTConfig {
	private String secret;
	private long expirationMs = 3600000; // 1 hour
	private String issuer = "finance-investment-service";
	private String audience = "audience";
	private long refreshExpirationMs = 86400000; // 24 hours
}
