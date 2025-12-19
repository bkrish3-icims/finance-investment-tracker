package com.labs.pfit.auth_service.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.labs.pfit.auth_service.dto.request.UserRequest;
import com.labs.pfit.auth_service.dto.response.UserResponse;
import com.labs.pfit.auth_service.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
	User toEntity(UserRequest userRequest);
	UserResponse toDto(User user);
}
