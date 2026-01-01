package com.labs.pfit.auth_service.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.labs.pfit.auth_service.dto.request.UserRequest;
import com.labs.pfit.auth_service.dto.response.UserResponse;
import com.labs.pfit.auth_service.entity.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
	@Mapping(target = "passwordHash", ignore = true)
	@Mapping(target = "previousPasswords", ignore = true)
	@Mapping(target = "active", ignore = true)
	@Mapping(target = "admin", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "username", source = "email")
	User toEntity(UserRequest userRequest);

	@Mapping(target = "username", source = "username")
	UserResponse toDto(User user);
}
