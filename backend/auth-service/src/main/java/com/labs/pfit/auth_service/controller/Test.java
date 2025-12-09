package com.labs.pfit.auth_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/test")
public class Test {
	
	@GetMapping("/hello")
	public String hello() {
		return "Hello from Auth Service!";
	}

}
