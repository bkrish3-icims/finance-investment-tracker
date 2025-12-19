package com.labs.pfit.auth_service.services.util;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class Encryptor {

	private final PasswordEncoder passwordEncoder;
	
	public String encryptPassword(String password) {
		return passwordEncoder.encode(password);
	}
	
	public boolean isPasswordMatch(String rawPassword, String encodedPassword) {
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}
	
}
