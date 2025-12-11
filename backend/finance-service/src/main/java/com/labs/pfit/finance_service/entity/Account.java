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
@Table(name = "accounts")
public class Account {
	@Id
	private UUID id;                // PRIMARY KEY
	private UUID userId;            // NOT NULL
	private String name;            // NOT NULL
	private String type;            // NOT NULL // 'BANK', 'CREDIT_CARD', 'WALLET', 'CASH'
	private String currency;        // NOT NULL // DEFAULT 'INR',
	private double openingBalance;  // NOT NULL DEFAULT 0,
	private Timestamp createdAt;    // NOT NULL DEFAULT now(),
	private Timestamp updated_at;   // NOT NULL DEFAULT now()
}
