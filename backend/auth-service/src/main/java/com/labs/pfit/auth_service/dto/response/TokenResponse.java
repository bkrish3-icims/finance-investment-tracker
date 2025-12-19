package com.labs.pfit.auth_service.dto.response;

import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {
	
	/**
	 * The generated access token.
	 */
	@JsonProperty("access_token")
	private String accessToken;
	
	/**
	 * The token type, always "Bearer".
	 */
	@Builder.Default
	@JsonProperty("token_type")
	private String tokenType = "Bearer";
	
	/**
	 * The expiration time of the access token in seconds.
	 */
	@JsonProperty("expires_in")
	private long expiresIn;
	
	/**
	 * The expiration date of the access token.
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	private LocalDateTime expiresAt;
	
	/**
	 * The refresh token for obtaining a new access token.
	 */
	private String refreshToken;
	
	/**
	 * The expiration date of the access token.
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	private LocalDateTime refreshExpiresAt;
	
}
