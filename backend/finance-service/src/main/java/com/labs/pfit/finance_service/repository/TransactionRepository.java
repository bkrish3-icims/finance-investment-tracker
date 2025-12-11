package com.labs.pfit.finance_service.repository;

import java.util.UUID;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.labs.pfit.finance_service.entity.Transaction;

public interface TransactionRepository extends ReactiveCrudRepository<Transaction, UUID> {
}
