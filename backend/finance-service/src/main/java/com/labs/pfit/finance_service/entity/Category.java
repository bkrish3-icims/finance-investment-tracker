package com.labs.pfit.finance_service.entity;

import java.sql.Timestamp;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;                // PRIMARY KEY,
	private UUID userId;            // NOT NULL, -- user-specific categories
	private String name;            // NOT NULL,
	private String type;            // NOT NULL, -- 'INCOME' or 'EXPENSE'
	private Timestamp createdAt;   // NOT NULL DEFAULT now(),
	private Timestamp updatedAt;   // NOT NULL DEFAULT now(),

}
