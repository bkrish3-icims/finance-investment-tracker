package com.labs.pfit.auth_service.entity;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
	@Id
	private UUID id;
	private String username;
	private String email;
	@Transient
	private String password;
	private String passwordHash;
	private String fullName;
	private boolean isActive;
	private boolean isAdmin;

	private Instant createdAt;
	private Instant updatedAt;

	private String previousPasswords;
}
