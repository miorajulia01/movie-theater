package com.example.demo.controller;


import com.example.demo.dto.response.UserResponse;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.security.CurrentUserService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {


	private final UserService userService;
	private final UserMapper userMapper;
	private final CurrentUserService currentUserService;

	@GetMapping("/me")
	public ResponseEntity<UserResponse> getCurrentUser() {
		User user = currentUserService.getCurrentUser();
		return ResponseEntity.ok(userMapper.toResponse(user));
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER','EMPLOYEE')")
	public ResponseEntity<UserResponse> getById(@PathVariable UUID id) {
		return ResponseEntity.ok(userMapper.toResponse(userService.findById(id)));
	}
}
