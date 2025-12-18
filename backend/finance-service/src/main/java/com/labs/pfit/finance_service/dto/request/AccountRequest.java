package com.labs.pfit.finance_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class AccountRequest {
	@NotNull(message = "Account Name cannot be null")
	@NotBlank(message = "Account Name cannot be blank")
	private String name;
	
	@NotNull(message = "Account Number cannot be null")
	@NotBlank(message = "Account Number cannot be blank")
	private String number;

	@NotNull(message = "Account Type cannot be null")
	@NotBlank(message = "Account Type cannot be blank")
	private String type;
	
	@NotNull(message = "Currency cannot be null")
	@NotBlank(message = "Currency cannot be blank")
	private String currency;
}