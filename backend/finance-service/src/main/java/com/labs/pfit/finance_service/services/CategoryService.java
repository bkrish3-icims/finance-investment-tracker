package com.labs.pfit.finance_service.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.labs.pfit.finance_service.dto.mapper.CategoryMapper;
import com.labs.pfit.finance_service.dto.request.CategoryRequest;
import com.labs.pfit.finance_service.dto.response.CategoryResponse;
import com.labs.pfit.finance_service.entity.Category;
import com.labs.pfit.finance_service.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CategoryService {
	private final CategoryRepository categoryRepository;
	private final CategoryMapper categoryMapper;
	
	public Mono<CategoryResponse> save(Mono<CategoryRequest> category) {
		return category
			       .map(categoryMapper::toEntiry)
			       .flatMap(categoryRepository::save)
			       .map(categoryMapper::toDto);
	}
	
	public Mono<Void> delete(UUID id) {
		return categoryRepository.deleteById(id);
	}

	public Flux<CategoryResponse> findAll() {
		return categoryRepository
			       .findAll()
			       .map(categoryMapper::toDto);
	}

	public Mono<CategoryResponse> findById(UUID id) {
		return categoryRepository
			       .findById(id)
			       .map(categoryMapper::toDto);
	}
}
