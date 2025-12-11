package com.labs.pfit.finance_service.repository;


import java.util.UUID;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.labs.pfit.finance_service.entity.Account;

public interface AccountRepository extends ReactiveCrudRepository<Account, UUID> {
}
