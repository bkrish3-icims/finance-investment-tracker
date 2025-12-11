package com.labs.pfit.finance_service.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.labs.pfit.finance_service.entity.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, UUID> {
}
