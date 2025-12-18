package com.labs.pfit.finance_service.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.labs.pfit.finance_service.dto.mapper.TxnMapper;
import com.labs.pfit.finance_service.dto.request.TransactionRequest;
import com.labs.pfit.finance_service.dto.response.TransactionResponse;
import com.labs.pfit.finance_service.entity.Transaction;
import com.labs.pfit.finance_service.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TransactionService {
	private final TransactionRepository transactionRepository;
	private final TxnMapper txnMapper;
	
	public Flux<TransactionResponse> findAll() {
		return transactionRepository
			       .findAll()
			       .map(txnMapper::toDto);
	}
	
	public Mono<TransactionResponse> findById(UUID id) {
		return transactionRepository
			       .findById(id)
			       .map(txnMapper::toDto);
	}
	
	public Mono<TransactionResponse> save(Mono<TransactionRequest> transaction) {
		return transaction
			       .map(txnMapper::toEntity)
			       .flatMap(transactionRepository::save)
			       .map(txnMapper::toDto);
	}
}
