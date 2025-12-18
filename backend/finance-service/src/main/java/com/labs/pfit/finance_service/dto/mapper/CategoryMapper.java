package com.labs.pfit.finance_service.dto.mapper;

import org.mapstruct.Mapper;

import com.labs.pfit.finance_service.dto.request.CategoryRequest;
import com.labs.pfit.finance_service.dto.response.CategoryResponse;
import com.labs.pfit.finance_service.entity.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
	Category toEntiry(CategoryRequest categoryRequest);
	CategoryResponse toDto(Category category);
}
