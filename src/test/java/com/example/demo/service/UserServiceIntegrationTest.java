package com.example.demo.service;

import com.example.demo.BaseIntegrationTest;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UserServiceIntegrationTest extends BaseIntegrationTest {

	@Autowired
	private UserService userService;

	@Test
	void shouldFindUserById() {
		User found = userService.findById(testUser.getId());

		assertThat(found).isNotNull();
		assertThat(found.getId()).isEqualTo(testUser.getId());
		assertThat(found.getEmail()).isEqualTo("john.doe@test.com");
		assertThat(found.getFirstName()).isEqualTo("John");
	}

	@Test
	void shouldThrowExceptionWhenUserNotFound() {
		UUID nonExistentId = UUID.randomUUID();

		assertThatThrownBy(() -> userService.findById(nonExistentId))
				.isInstanceOf(ResourceNotFoundException.class)
				.hasMessage("User not found with id: " + nonExistentId);
	}

	@Test
	void shouldUpdateUser() {
		// When
		User updated = userService.update(
				testUser.getId(),
				"Johnathan",
				"Doe",
				LocalDate.of(1991, 2, 2),
				"+9876543210"
		);

		// Then
		assertThat(updated.getFirstName()).isEqualTo("Johnathan");
		assertThat(updated.getLastName()).isEqualTo("Doe");
		assertThat(updated.getBirthdate()).isEqualTo(LocalDate.of(1991, 2, 2));
		assertThat(updated.getPhone()).isEqualTo("+9876543210");

		// Vérifier que les changements sont persistés
		User fromDb = userRepository.findById(testUser.getId()).orElseThrow();
		assertThat(fromDb.getFirstName()).isEqualTo("Johnathan");
	}

	@Test
	void shouldLoadUserByUsername() {
		UserDetails userDetails = userService.loadUserByUsername("john.doe@test.com");

		assertThat(userDetails).isNotNull();
		assertThat(userDetails.getUsername()).isEqualTo("john.doe@test.com");
		assertThat(userDetails.getAuthorities()).hasSize(1);
		assertThat(userDetails.getAuthorities().iterator().next().getAuthority())
				.isEqualTo("ROLE_CLIENT");
	}
}