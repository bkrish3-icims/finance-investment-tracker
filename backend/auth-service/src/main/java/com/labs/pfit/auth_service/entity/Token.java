package com.labs.pfit.auth_service.entity;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tokens")
public class Token {

	@Id
	private UUID id;
	private UUID userId;
	private String tokenHash;
	private Instant expiresAt;
	private boolean isActive;
	private boolean revoked;
	private boolean used;
	private Instant updatedAt;
	
}
