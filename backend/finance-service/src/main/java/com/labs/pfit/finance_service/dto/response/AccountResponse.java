package com.labs.pfit.finance_service.dto.response;

import java.sql.Timestamp;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AccountResponse {
	private UUID id;                // PRIMARY KEY
	private String name;            // NOT NULL
	private String number;          // NOT NULL UNIQUE
	private String type;            // NOT NULL // 'BANK', 'CREDIT_CARD', 'WALLET', 'CASH'
	private String currency;        // NOT NULL // DEFAULT 'INR',
	private double openingBalance;  // NOT NULL DEFAULT 0,
	private Timestamp createdAt;    // NOT NULL DEFAULT now(),
	private Timestamp updatedAt;   // NOT NULL DEFAULT now()
}
