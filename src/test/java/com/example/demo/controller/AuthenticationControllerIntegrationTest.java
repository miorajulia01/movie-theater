package com.example.demo.controller;

import com.example.demo.BaseIntegrationTest;
import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.request.RegisterRequest;
import com.example.demo.entity.User;
import com.example.demo.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AuthenticationControllerIntegrationTest extends BaseIntegrationTest {

	@Test
	void shouldRegisterUserSuccessfully() throws Exception {
		RegisterRequest request = RegisterRequest.builder()
				.firstName("Jane")
				.lastName("Smith")
				.email("jane.smith@test.com")
				.password("password123")
				.phone("+1234567890")
				.birthdate(LocalDate.of(1995, 6, 20))
				.build();

		mockMvc.perform(post("/api/auth/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.token").isNotEmpty())
				.andExpect(jsonPath("$.tokenType").value("Bearer"))
				.andExpect(jsonPath("$.user.email").value("jane.smith@test.com"))
				.andExpect(jsonPath("$.user.role").value("CLIENT"));

		// Vérifier que l'utilisateur a bien été créé en base
		User savedUser = userRepository.findByEmail("jane.smith@test.com").orElse(null);
		assert savedUser != null;
		assert savedUser.getFirstName().equals("Jane");
		assert savedUser.getRole() == Role.CLIENT;
	}

	@Test
	void shouldNotRegisterWithExistingEmail() throws Exception {
		RegisterRequest request = RegisterRequest.builder()
				.firstName("John")
				.lastName("Doe")
				.email("john.doe@test.com") // Email déjà utilisé
				.password("password123")
				.phone("+1234567890")
				.birthdate(LocalDate.of(1990, 1, 1))
				.build();

		mockMvc.perform(post("/api/auth/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("An account already exists with this email"));
	}

	@Test
	void shouldRegisterWithInvalidEmail() throws Exception {
		RegisterRequest request = RegisterRequest.builder()
				.firstName("Jane")
				.lastName("Smith")
				.email("invalid-email")
				.password("password123")
				.phone("+1234567890")
				.birthdate(LocalDate.of(1995, 6, 20))
				.build();

		mockMvc.perform(post("/api/auth/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isBadRequest());
	}

	@Test
	void shouldLoginSuccessfully() throws Exception {
		LoginRequest request = LoginRequest.builder()
				.email("john.doe@test.com")
				.password("password123")
				.build();

		mockMvc.perform(post("/api/auth/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.token").isNotEmpty())
				.andExpect(jsonPath("$.tokenType").value("Bearer"))
				.andExpect(jsonPath("$.user.email").value("john.doe@test.com"))
				.andExpect(jsonPath("$.user.firstName").value("John"));
	}

	@Test
	void shouldNotLoginWithWrongPassword() throws Exception {
		LoginRequest request = LoginRequest.builder()
				.email("john.doe@test.com")
				.password("wrongpassword")
				.build();

		mockMvc.perform(post("/api/auth/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("Invalid email or password"));
	}

	@Test
	void shouldNotLoginWithNonExistentEmail() throws Exception {
		LoginRequest request = LoginRequest.builder()
				.email("nonexistent@test.com")
				.password("password123")
				.build();

		mockMvc.perform(post("/api/auth/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("Invalid email or password"));
	}
}