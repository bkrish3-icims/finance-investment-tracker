package com.labs.pfit.auth_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
	private String email;
	private String password;
	private String fullName;
}
