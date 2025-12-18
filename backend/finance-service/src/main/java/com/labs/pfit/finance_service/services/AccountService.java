package com.labs.pfit.finance_service.services;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.labs.pfit.finance_service.dto.mapper.AccountMapper;
import com.labs.pfit.finance_service.dto.request.AccountRequest;
import com.labs.pfit.finance_service.dto.response.AccountResponse;
import com.labs.pfit.finance_service.entity.Account;
import com.labs.pfit.finance_service.repository.AccountRepository;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class AccountService {

	private final AccountRepository accountRepository;
	private final AccountMapper accountMapper;

	public Mono<AccountResponse> findByAccountNumber(UUID accountNumber) {
		return accountRepository
			       .findById(accountNumber)
			       .map(accountMapper::toDto);
	}

	public Flux<AccountResponse> listAllAccounts() {
		return accountRepository
			       .findAll()
			       .map(accountMapper::toDto);
	}

	public Mono<AccountResponse> createNewAccount(Mono<AccountRequest> account) {
		return account
			       .map(accountMapper::toEntity)
			       .map(e -> {
					   e.setId(UUID.randomUUID());
					   e.setUserId(UUID.randomUUID()); // Until Auth Service is implemented
				       e.setOpeningBalance(0);
				       e.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
					   return e;
			       })
			       .flatMap(accountRepository::save)
			       .map(accountMapper::toDto);
	}

	public Mono<AccountResponse> updateAccount(UUID accountId, Mono<AccountRequest> account) {
		return Mono.fromDirect(accountRepository.findById(accountId))
			       .switchIfEmpty(Mono.error(new Exception("Account not found with id: " + accountId)))
			       .flatMap(accountEntity -> account.map(accountRequest -> {
					   accountEntity.setName(accountRequest.getName());
					   accountEntity.setNumber(accountRequest.getNumber());
					   accountEntity.setType(accountRequest.getType());
					   accountEntity.setCurrency(accountRequest.getCurrency());
					   accountEntity.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
					   return accountEntity;
				   }))
			       .flatMap(accountRepository::save)
			       .map(accountMapper::toDto);
	}
	
}
