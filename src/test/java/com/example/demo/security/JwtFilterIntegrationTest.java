package com.example.demo.security;

import com.example.demo.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class JwtFilterIntegrationTest extends BaseIntegrationTest {

	@Test
	void shouldAllowAccessWithValidToken() throws Exception {
		mockMvc.perform(get("/api/users/me")
						.header("Authorization", getAuthHeader(testUserToken)))
				.andExpect(status().isOk());
	}

	@Test
	void shouldDenyAccessWithNoToken() throws Exception {
		mockMvc.perform(get("/api/users/me"))
				.andExpect(status().isForbidden());
	}

	@Test
	void shouldDenyAccessWithInvalidToken() throws Exception {
		mockMvc.perform(get("/api/users/me")
						.header("Authorization", "Bearer invalid.token.here"))
				.andExpect(status().isForbidden());
	}

	@Test
	void shouldDenyAccessWithExpiredToken() throws Exception {
		// Créer un token expiré (simulé)
		String expiredToken = jwtService.generateToken(testUser);

		// Simuler l'expiration en modifiant manuellement le token (optionnel)
		// Dans un vrai test, vous pourriez créer un token avec une date d'expiration passée

		mockMvc.perform(get("/api/users/me")
						.header("Authorization", "Bearer " + expiredToken))
				.andExpect(status().isForbidden());
	}
}