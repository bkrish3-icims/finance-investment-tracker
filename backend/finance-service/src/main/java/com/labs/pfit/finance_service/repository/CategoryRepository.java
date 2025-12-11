package com.labs.pfit.finance_service.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.labs.pfit.finance_service.entity.Category;

public interface CategoryRepository extends CrudRepository<Category, UUID> {
}
