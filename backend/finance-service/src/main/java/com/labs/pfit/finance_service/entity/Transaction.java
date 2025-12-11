package com.labs.pfit.finance_service.entity;

import java.sql.Date;
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
@Table(name = "transactions")
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private UUID id;                // PRIMARY KEY,
	private UUID userId;            // NOT NULL,
	private UUID account_id;        // NOT NULL,
	private UUID category_id;       // NOT NULL,
	private String type;            // NOT NULL, -- 'INCOME' or 'EXPENSE'
	private double amount;          // NOT NULL,
	private Date transaction_date;  // NOT NULL,
	private String description;
	private Timestamp createdAt;    // NOT NULL DEFAULT now(),
	private Timestamp updatedAt;    // NOT NULL DEFAULT now(),
}
