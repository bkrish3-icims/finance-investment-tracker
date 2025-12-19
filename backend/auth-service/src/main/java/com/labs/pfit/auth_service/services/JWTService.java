package com.labs.pfit.auth_service.services;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.labs.pfit.auth_service.config.JWTConfig;
import com.labs.pfit.auth_service.dto.request.LoginRequest;
import com.labs.pfit.auth_service.dto.response.LoginResponse;
import com.labs.pfit.auth_service.dto.response.TokenResponse;
import com.labs.pfit.auth_service.entity.Token;
import com.labs.pfit.auth_service.error.InvalidRefreshTokenException;
import com.labs.pfit.auth_service.repo.TokenRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class JWTService {

	private final JWTConfig jwtConfig;
	private final TokenRepository tokenRepository;

	public String generateToken(String username, String email, long expirationMs) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + jwtConfig.getExpirationMs());
		
		Map<String, Object> claims = new HashMap<>();
		claims.put("email", email);
		
		log.info("Generating access token for user: {}", username);
		
		return Jwts.builder()
			       .setClaims(claims)
			       .setSubject(username)
			       .setIssuedAt(now)
			       .setExpiration(expiryDate)
			       .setIssuer(jwtConfig.getIssuer())
			       .setAudience(jwtConfig.getAudience())
			       .setId(UUID.randomUUID().toString())
			       .signWith(Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes()), SignatureAlgorithm.HS512)
			       .compact();
	}

	public TokenResponse generateTokens(String username, String email) {
		LocalDateTime expiresAt = LocalDateTime.now().plus(jwtConfig.getExpirationMs(), ChronoUnit.MILLIS);
		LocalDateTime refreshExpiresAt = LocalDateTime.now().plus(jwtConfig.getRefreshExpirationMs(), ChronoUnit.MILLIS);

		return TokenResponse.builder()
			       .accessToken(generateToken(username, email, jwtConfig.getExpirationMs()))
			       .tokenType("Bearer")
			       .expiresIn(jwtConfig.getExpirationMs() / 1000)
			       .expiresAt(expiresAt)
			       .refreshToken(generateToken(username, email, jwtConfig.getRefreshExpirationMs()))
			       .refreshExpiresAt(refreshExpiresAt)
			       .build();
	}

	@Transactional
	public void store(UUID userId, TokenResponse tokenResponse) {
		Token token = new Token();
		token.setId(UUID.randomUUID());
		token.setUserId(userId);
		token.setTokenHash(tokenResponse.getRefreshToken());
		token.setExpiresAt(Timestamp.valueOf(tokenResponse.getRefreshExpiresAt()));
		token.setRevoked(false);
		token.setUsed(false);
		
		tokenRepository.save(token);
	}
	
	@Transactional
	public Mono<Token> verifyAndRotate(String token) {
		return tokenRepository.findByTokenHash(token.trim())
			       .filter(rt -> !rt.isRevoked() && !rt.isUsed())
			       .switchIfEmpty(Mono.error(new InvalidRefreshTokenException("Invalid refresh token")))
			       .flatMap(rt -> {
					   if(rt.getExpiresAt().toInstant().isBefore(Instant.now())) {
						   rt.setUsed(true);
						   rt.setRevoked(true);
						   return tokenRepository.save(rt)
							          .then(Mono.error(new InvalidRefreshTokenException("Refresh token has expired")));
					   }
					   
					   // mark used immediately (rotation)
					   rt.setUsed(true);
					   rt.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
					   return tokenRepository.save(rt)
						          .then(Mono.error(new InvalidRefreshTokenException("Refresh token has expired")));
			});
	}
	
	@Transactional
	public Mono<Void> rotate(UUID userId, TokenResponse newRawRefreshToken) {
		Token newToken = new Token();
		newToken.setId(UUID.randomUUID());
		newToken.setUserId(userId);
		newToken.setTokenHash(newRawRefreshToken.getRefreshToken());
		newToken.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
		newToken.setExpiresAt(Timestamp.valueOf(newRawRefreshToken.getRefreshExpiresAt()));
		newToken.setRevoked(false);
		newToken.setUsed(false);
		
		return tokenRepository.save(newToken).then();
	}

}
