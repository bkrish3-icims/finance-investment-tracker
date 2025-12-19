package com.labs.pfit.auth_service.repo;

import java.util.UUID;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.labs.pfit.auth_service.entity.User;

import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User, UUID> {
	Mono<User> findUserByEmail(String email);
}
