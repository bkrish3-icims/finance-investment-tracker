package com.labs.pfit.finance_service.dto.request;

import java.sql.Date;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class TransactionRequest {
	private UUID accountId;
	private UUID categoryId;
	private String type;
	private double amount;
	private Date transactionDate;
	private String description;
}
