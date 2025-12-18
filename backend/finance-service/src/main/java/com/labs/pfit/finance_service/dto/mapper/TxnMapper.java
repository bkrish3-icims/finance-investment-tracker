package com.labs.pfit.finance_service.dto.mapper;

import org.mapstruct.Mapper;

import com.labs.pfit.finance_service.dto.request.TransactionRequest;
import com.labs.pfit.finance_service.dto.response.TransactionResponse;
import com.labs.pfit.finance_service.entity.Transaction;

@Mapper(componentModel = "spring")
public interface TxnMapper {
	Transaction toEntity(TransactionRequest request);
	TransactionResponse toDto(Transaction transaction);
}
