package com.labs.pfit.finance_service.repository;

import java.util.UUID;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.labs.pfit.finance_service.entity.Category;

@Repository
public interface CategoryRepository extends ReactiveCrudRepository<Category, UUID> {
}
