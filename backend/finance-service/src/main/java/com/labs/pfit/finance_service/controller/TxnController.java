package com.labs.pfit.finance_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.labs.pfit.finance_service.dto.request.TransactionRequest;
import com.labs.pfit.finance_service.dto.response.TransactionResponse;
import com.labs.pfit.finance_service.services.TransactionService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/finance/v1/transactions")
@RequiredArgsConstructor
public class TxnController {

	private final TransactionService transactionService;

	@GetMapping
	public Flux<ResponseEntity<TransactionResponse>> getAllTransactions() {
		return transactionService
			       .findAll()
			       .map(ResponseEntity::ok);
	}

	@GetMapping("/{id}")
	public Mono<ResponseEntity<TransactionResponse>> getTransactionById(@PathVariable String id) {
		return transactionService
			       .findById(java.util.UUID.fromString(id))
			       .map(ResponseEntity::ok)
			       .defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@PostMapping
	public Mono<ResponseEntity<TransactionResponse>> createNewTransaction(@RequestBody Mono<TransactionRequest> request) {
		return transactionService.save(request)
			       .map(ResponseEntity::ok);
	}

}
