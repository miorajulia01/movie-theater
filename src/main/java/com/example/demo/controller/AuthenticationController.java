package com.example.demo.controller;


import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.request.RegisterRequest;
import com.example.demo.dto.response.AuthResponse;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

	private final AuthenticationService authenticationService;
	private final UserMapper userMapper;


	@PostMapping("/register")
	public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
		User user = authenticationService.register(request);
		String token = authenticationService.generateToken(user);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(
						AuthResponse.builder()
								.token(token)
								.tokenType("Bearer")
								.user(userMapper.toResponse(user))
								.build());
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
		User user = authenticationService.login(request);
		String token = authenticationService.generateToken(user);
		return ResponseEntity.ok(
				AuthResponse.builder()
						.token(token)
						.tokenType("Bearer")
						.user(userMapper.toResponse(user))
						.build());
	}
}
