package com.labs.pfit.finance_service.repository;


import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.labs.pfit.finance_service.entity.Account;

public interface AccountRepository extends CrudRepository<Account, UUID> {
}
