package com.labs.pfit.auth_service.entity;

import java.sql.Timestamp;
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
	private Timestamp expiresAt;
	private boolean revoked;
	private boolean used;
	private Timestamp updatedAt;
	
}
