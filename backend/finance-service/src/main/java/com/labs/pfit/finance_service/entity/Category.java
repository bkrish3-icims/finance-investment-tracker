package com.labs.pfit.finance_service.entity;

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
@Table(name = "categories")
public class Category {
	@Id
	private UUID id;                // PRIMARY KEY,
	private UUID userId;            // NOT NULL, -- user-specific categories
	private String name;            // NOT NULL,
	private String type;            // NOT NULL, -- 'INCOME' or 'EXPENSE'
	private Timestamp createdAt;    // NOT NULL DEFAULT now(),
	private Timestamp updatedAt;    // NOT NULL DEFAULT now(),
}
