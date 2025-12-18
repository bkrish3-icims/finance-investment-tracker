package com.labs.pfit.finance_service.dto.response;

import java.sql.Date;
import java.util.UUID;

public class TransactionResponse {
	private UUID id;
	private UUID accountId;
	private UUID categoryId;
	private String type;
	private double amount;
	private double balance;
	private Date transactionDate;
}
