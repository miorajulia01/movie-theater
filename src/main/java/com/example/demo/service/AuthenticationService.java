package com.example.demo.service;

import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.request.RegisterRequest;
import com.example.demo.entity.User;
import com.example.demo.enums.Role;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	public User register(RegisterRequest request) {
		if (userRepository.existsByEmail(request.getEmail())) {
			throw new BadRequestException("An account already exists with this email");
		}
		User user =
				User.builder()
						.firstName(request.getFirstName())
						.lastName(request.getLastName())
						.birthdate(request.getBirthdate())
						.email(request.getEmail())
						.password(passwordEncoder.encode(request.getPassword()))
						.phone(request.getPhone())
						.role(Role.CLIENT)
						.build();
		return userRepository.save(user);
	}

	public User login(LoginRequest request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		return userRepository
				.findByEmail(request.getEmail())
				.orElseThrow(() -> new BadRequestException("Invalid email or password"));
	}

	public String generateToken(User user) {
		return jwtService.generateToken(user);
	}
}
