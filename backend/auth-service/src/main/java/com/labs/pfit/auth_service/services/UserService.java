package com.labs.pfit.auth_service.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.labs.pfit.auth_service.dto.mapper.UserMapper;
import com.labs.pfit.auth_service.dto.request.UserRequest;
import com.labs.pfit.auth_service.dto.response.UserResponse;
import com.labs.pfit.auth_service.entity.User;
import com.labs.pfit.auth_service.repo.UserRepository;
import com.labs.pfit.auth_service.services.util.Encryptor;
import com.labs.pfit.auth_service.services.util.UsernameGenerator;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserMapper userMapper;
	private final UserRepository userRepository;
	private final Encryptor encryptor;
	
	public Mono<UserResponse> createUser(Mono<UserRequest> userRequest) {
		return userRequest
			       .map(userMapper::toEntity)
			       .map(user -> {
					   user.setUsername(UsernameGenerator.generateUsername(user.getFullName()));    // Generate random String with 4 char prefix of full name
				       user.setPasswordHash(encryptor.encryptPassword(user.getPassword()));
					   user.setActive(true);    // TODO: later we can implement email verification / validation to set the value
				       user.setAdmin(false);
					   user.setPreviousPasswords(encryptor.encryptPassword(user.getPassword() + "<>@<>@<>"));
					   return user;
			       })
			       .flatMap(userRepository::save)
			       .map(userMapper::toDto);
	}

	public Mono<User> getUserByEmail(String email) {
		return userRepository.findUserByEmail(email);
	}
	
	public Mono<User> getUserById(UUID id) {
		return userRepository.findById(id);
	}
	
	public boolean isPasswordMatch(String rawPassword, String encryptedPassword) {
		return encryptor.isPasswordMatch(rawPassword, encryptedPassword);
	}
}
