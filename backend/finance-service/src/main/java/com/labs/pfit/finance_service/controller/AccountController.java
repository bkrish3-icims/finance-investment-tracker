package com.labs.pfit.finance_service.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.labs.pfit.finance_service.dto.request.AccountRequest;
import com.labs.pfit.finance_service.dto.response.AccountResponse;
import com.labs.pfit.finance_service.services.AccountService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/finance/v1/accounts")
@RequiredArgsConstructor
public class AccountController {
	
	private final AccountService accountService;

	@GetMapping
	public Flux<ResponseEntity<AccountResponse>> accounts() {
		return accountService.listAllAccounts()
			       .map(ResponseEntity::ok);
	}

	@GetMapping("/{accountNumber}")
	public Mono<ResponseEntity<AccountResponse>> findByAccountNumber(@PathVariable UUID accountNumber) {
		return accountService.findByAccountNumber(accountNumber)
				.map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@PostMapping
	public Mono<ResponseEntity<AccountResponse>> createNewAccoount(@RequestBody Mono<AccountRequest> account) {
		return accountService.createNewAccount(account)
			       .map(ResponseEntity::ok);
	}

	@PutMapping("/{accountId}")
	public Mono<ResponseEntity<AccountResponse>> updateAccount(@PathVariable UUID accountId, @RequestBody Mono<AccountRequest> account) {
		return accountService.updateAccount(accountId, account)
			       .map(ResponseEntity::ok)
			       .defaultIfEmpty(ResponseEntity.notFound().build());
	}

}
