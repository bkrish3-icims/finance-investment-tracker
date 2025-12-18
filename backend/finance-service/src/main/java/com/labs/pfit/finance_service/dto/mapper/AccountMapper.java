package com.labs.pfit.finance_service.dto.mapper;

import org.mapstruct.Mapper;

import com.labs.pfit.finance_service.dto.request.AccountRequest;
import com.labs.pfit.finance_service.dto.response.AccountResponse;
import com.labs.pfit.finance_service.entity.Account;

@Mapper(componentModel = "spring")
public interface AccountMapper {
	Account toEntity(AccountRequest accountRequest);
	AccountResponse toDto(Account account);
}
