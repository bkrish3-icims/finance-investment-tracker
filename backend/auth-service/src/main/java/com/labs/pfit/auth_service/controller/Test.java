package com.labs.pfit.auth_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth/test")
public class Test {
	
	@GetMapping("/hello")
	public Mono<String> hello() {
		return Mono.just("Hello from Auth Service!");
	}

}
