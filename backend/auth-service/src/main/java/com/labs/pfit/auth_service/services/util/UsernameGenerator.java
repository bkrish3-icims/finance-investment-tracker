package com.labs.pfit.auth_service.services.util;

import java.security.SecureRandom;

public class UsernameGenerator {
	private static final SecureRandom random = new SecureRandom();
	
	public static String generateUsername(String fullName) {
		String[] parts = fullName.trim().toLowerCase().split("\\s+");
		if (parts.length < 2) return null;
		
		String firstName = parts[0];
		
		String prefix = firstName.substring(0, Math.min(4, firstName.length()));
		int randNum = random.nextInt(100);
		return prefix + (fullName.length() % 100) + String.format("%02d", randNum);
	}
}
