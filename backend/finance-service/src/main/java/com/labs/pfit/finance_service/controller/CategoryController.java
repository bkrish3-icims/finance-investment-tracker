package com.labs.pfit.finance_service.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.labs.pfit.finance_service.dto.request.CategoryRequest;
import com.labs.pfit.finance_service.dto.response.CategoryResponse;
import com.labs.pfit.finance_service.services.CategoryService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/finance/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

	private final CategoryService categoryService;

	@GetMapping
	public Flux<ResponseEntity<CategoryResponse>> listCategories() {
		return categoryService
			       .findAll()
			       .map(ResponseEntity::ok);
	}

	@PostMapping
	public Mono<ResponseEntity<CategoryResponse>> addCategory(@RequestBody Mono<CategoryRequest> categoryRequest) {
		return categoryService
			       .save(categoryRequest)
			       .map(ResponseEntity::ok);
	}

	@DeleteMapping
	public Mono<ResponseEntity<Void>> deleteCategory(UUID categoryId) {
		return categoryService.delete(categoryId)
			       .map(ResponseEntity::ok);
	}
	
}