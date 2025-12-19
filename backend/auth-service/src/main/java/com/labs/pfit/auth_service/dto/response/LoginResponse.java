package com.labs.pfit.auth_service.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
	@JsonProperty("access_token")
	private String accessToken;
	
	@JsonProperty("token_type")
	private String tokenType;
	
	@JsonProperty("expires_in")
	private long expiresIn;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	private LocalDateTime expiresAt;
	
	@JsonProperty("refresh_token")
	private String refreshToken;
	
}
