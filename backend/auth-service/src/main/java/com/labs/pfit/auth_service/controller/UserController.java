package com.labs.pfit.auth_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.labs.pfit.auth_service.dto.request.LoginRequest;
import com.labs.pfit.auth_service.dto.request.RefreshRequest;
import com.labs.pfit.auth_service.dto.request.UserRequest;
import com.labs.pfit.auth_service.dto.response.LoginResponse;
import com.labs.pfit.auth_service.dto.response.TokenResponse;
import com.labs.pfit.auth_service.dto.response.UserResponse;
import com.labs.pfit.auth_service.services.JWTService;
import com.labs.pfit.auth_service.services.UserService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final JWTService jwtService;
	
	@PostMapping("/signup")
	public Mono<ResponseEntity<UserResponse>> createUser(@RequestBody Mono<UserRequest> userRequest) {
		return userService.createUser(userRequest)
				.map(ResponseEntity::ok);
	}

	@PostMapping("/login")
	public Mono<ResponseEntity<TokenResponse>> login(@RequestBody Mono<LoginRequest> request) {
		return request.flatMap(req -> userService
			                              .getUserByEmail(req.getUsernameOrEmail())
			                              .switchIfEmpty(Mono.error(new BadCredentialsException("Invalid username; User not found")))
			                              .flatMap(user ->
				                                       userService.isPasswordMatch(req.getPassword(), user.getPasswordHash())
					                                       ? Mono.just(user) : Mono.error(new BadCredentialsException("Mismatch password")))
			                              .map(user -> {
											  TokenResponse tokenResponse = jwtService.generateTokens(user.getUsername(), user.getEmail());
											  jwtService.store(user.getId(), tokenResponse);
											  return tokenResponse;
										  })
			                              .map(ResponseEntity::ok));
	}

	@PostMapping("/refresh-token")
	public Mono<ResponseEntity<TokenResponse>> refreshToken(Mono<RefreshRequest> request) {
		return request.flatMap(req -> jwtService.verifyAndRotate(req.getToken())
			                              .flatMap(rt -> userService.getUserById(rt.getUserId()))
			                              .map(user -> {
											  TokenResponse tokenResponse = jwtService.generateTokens(user.getUsername(), user.getEmail());
											  jwtService.rotate(user.getId(), tokenResponse);
											  return tokenResponse;
										  })
			                              .map(ResponseEntity::ok));
	}
}