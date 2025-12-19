package com.labs.pfit.auth_service.error;

public class InvalidRefreshTokenException extends RuntimeException {
	public InvalidRefreshTokenException(String message) {
		super(message);
	}
}
