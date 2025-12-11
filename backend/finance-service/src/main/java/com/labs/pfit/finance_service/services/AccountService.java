package com.labs.pfit.finance_service.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.labs.pfit.finance_service.entity.Account;
import com.labs.pfit.finance_service.repository.AccountRepository;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class AccountService {

	private final AccountRepository accountRepository;

	public Mono<Account> findByAccountNumber(String accountNumber) {
		return accountRepository.findById(UUID.fromString(accountNumber));
	}
	
	public Flux<Account> findAll() {
		return accountRepository.findAll();
	}
}
