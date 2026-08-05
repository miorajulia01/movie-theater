package com.example.demo;

import com.example.demo.entity.User;
import com.example.demo.enums.Role;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
public abstract class BaseIntegrationTest {

	@Container
	@ServiceConnection
	static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
			.withDatabaseName("testdb")
			.withUsername("test")
			.withPassword("test");

	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	protected ObjectMapper objectMapper;

	@Autowired
	protected UserRepository userRepository;

	@Autowired
	protected PasswordEncoder passwordEncoder;

	@Autowired
	protected JwtService jwtService;

	protected User testUser;
	protected String testUserToken;

	@BeforeEach
	void setUpBase() {
		userRepository.deleteAll();
		createTestUser();
	}

	protected void createTestUser() {
		testUser = User.builder()
				.id(UUID.randomUUID())
				.firstName("John")
				.lastName("Doe")
				.email("john.doe@test.com")
				.password(passwordEncoder.encode("password123"))
				.phone("+1234567890")
				.birthdate(LocalDate.of(1990, 1, 1))
				.role(Role.CLIENT)
				.build();

		testUser = userRepository.save(testUser);
		testUserToken = jwtService.generateToken(testUser);
	}

	protected User createAdminUser() {
		User admin = User.builder()
				.id(UUID.randomUUID())
				.firstName("Admin")
				.lastName("User")
				.email("admin@test.com")
				.password(passwordEncoder.encode("admin123"))
				.phone("+0987654321")
				.birthdate(LocalDate.of(1985, 5, 15))
				.role(Role.ADMIN)
				.build();

		return userRepository.save(admin);
	}

	protected String getAuthHeader(String token) {
		return "Bearer " + token;
	}
}