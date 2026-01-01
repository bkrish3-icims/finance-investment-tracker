package com.labs.pfit.auth_service.services;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.hash.Hashing;
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
import reactor.core.scheduler.Schedulers;

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

	public Mono<TokenResponse> generateTokens(String username, String email) {
		LocalDateTime expiresAt = LocalDateTime.now().plus(jwtConfig.getExpirationMs(), ChronoUnit.MILLIS);
		LocalDateTime refreshExpiresAt = LocalDateTime.now().plus(jwtConfig.getRefreshExpirationMs(), ChronoUnit.MILLIS);

		return Mono.fromCallable(() -> TokenResponse.builder()
			       .accessToken(generateToken(username, email, jwtConfig.getExpirationMs()))
			       .tokenType("Bearer")
			       .expiresIn(jwtConfig.getExpirationMs() / 1000)
			       .expiresAt(expiresAt)
			       .refreshToken(generateToken(username, email, jwtConfig.getRefreshExpirationMs()))
			       .refreshExpiresAt(refreshExpiresAt)
			       .build()
		).subscribeOn(Schedulers.boundedElastic());
	}

	@Transactional
	public Mono<Void> store(UUID userId, TokenResponse tokenResponse) {
		log.info("Storing refresh token for userId {}", userId);
		
		String tokenHash = tokenHash(tokenResponse.getRefreshToken());
		
		log.info("Storing up token hash: {}", tokenHash.substring(0, 16) + "...");

		Token token = new Token();
		token.setUserId(userId);
		token.setTokenHash(tokenHash);
		token.setExpiresAt(tokenResponse.getRefreshExpiresAt().toInstant(ZoneOffset.UTC));
		token.setRevoked(false);
		token.setUsed(false);
		token.setActive(true);
		
		log.info("Storing refresh token for userId {} db call init", userId);
		return tokenRepository.save(token)
			       .doOnNext(token1 -> log.info("Storing refresh token for userId {} db call completed", userId))
			       .then();
	}
	
	@Transactional
	public Mono<Token> verifyAndRotate(String token) {
		String tokenHash = tokenHash(token);
		log.info("Looking up token hash: {}", tokenHash.substring(0, 16) + "...");
		
		return tokenRepository.findByTokenHash(tokenHash)
			       .doOnNext(t -> log.info("Token {}", t.toString()))
			       .filter(rt -> !rt.isRevoked() && !rt.isUsed())
			       .switchIfEmpty(Mono.error(new InvalidRefreshTokenException("Invalid refresh token")))
			       .flatMap(rt -> {
					   Instant now = Instant.now();
					   if(rt.getExpiresAt().isBefore(Instant.now())) {
						   return tokenRepository.markTokenUsedAndRevoked(rt.getId(), now)
							          .filter(rows -> rows > 0)
							          .switchIfEmpty(Mono.error(new InvalidRefreshTokenException("Token already used")))
							          .flatMap(rows -> tokenRepository.findById(rt.getId()));
					   }
					   
					   // mark used immediately (rotation)
					   return tokenRepository.markTokenUsed(rt.getId(), now)
						          .filter(rows -> rows > 0)
						          .switchIfEmpty(Mono.error(new InvalidRefreshTokenException("Token already used")))
						          .flatMap(rows -> tokenRepository.findById(rt.getId()));
			});
	}
	
	@Transactional
	public Mono<Void> rotate(UUID userId, TokenResponse newRawRefreshToken) {
		String tokenHash = tokenHash(newRawRefreshToken.getRefreshToken());
		
		Token newToken = new Token();
		newToken.setUserId(userId);
		newToken.setTokenHash(tokenHash);
		newToken.setUpdatedAt(Instant.now());
		newToken.setExpiresAt(newRawRefreshToken.getRefreshExpiresAt().toInstant(ZoneOffset.UTC));
		newToken.setRevoked(false);
		newToken.setUsed(false);
		
		return tokenRepository.save(newToken).then();
	}

	private String tokenHash(String token) {
		return Hashing.sha256()
			                   .hashString(token, StandardCharsets.UTF_8)
			                   .toString();
	}
}
