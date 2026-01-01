package com.labs.pfit.auth_service.repo;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.labs.pfit.auth_service.entity.Token;

import reactor.core.publisher.Mono;

@Repository
public interface TokenRepository extends ReactiveCrudRepository<Token,UUID> {
	Mono<Token> findByTokenHash(String tokenHash);
	
	@Modifying
	@Query("UPDATE tokens SET used = true, revoked = false, updated_at = :updatedAt WHERE id = :id  AND revoked = false AND used = false")
	Mono<Long> markTokenUsed(@Param("id") UUID id, @Param("updatedAt") Instant updatedAt);
	
	@Modifying
	@Query("UPDATE tokens SET used = true, revoked = true, updated_at = :updatedAt WHERE id = :id AND revoked = false AND used = false")
	Mono<Long> markTokenUsedAndRevoked(@Param("id") UUID id, @Param("updatedAt") Instant updatedAt);

}
