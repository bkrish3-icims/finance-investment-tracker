package com.labs.pfit.auth_service.repo;

import java.util.UUID;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.labs.pfit.auth_service.entity.Token;

import reactor.core.publisher.Mono;

@Repository
public interface TokenRepository extends ReactiveCrudRepository<Token,UUID> {
	Mono<Token> findByTokenHash(String tokenHash);
}
