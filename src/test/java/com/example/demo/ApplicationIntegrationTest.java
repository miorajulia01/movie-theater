package com.example.demo;

import com.example.demo.conf.FacadeIT;
import com.example.demo.entity.*;
import com.example.demo.enums.Genre;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import java.time.Duration;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
class ApplicationIntegrationTest extends FacadeIT {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    // Désactive la sécurité HTTP pour les tests d'intégration
    @TestConfiguration
    static class NoSecurityConfig {
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http.csrf(csrf -> csrf.disable())
                    .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
            return http.build();
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldTestAllEndpoints() throws Exception {

        // 1. Création d'un Movie
        JMovie movie = JMovie.builder()
                .title("Inception")
                .description("Sci-fi thriller")
                .duration(Duration.ofMinutes(148))
                .genre(Set.of(Genre.ACTION))
                .build();

        String movieResponseJson = mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movie)))
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();

        JMovie createdMovie = objectMapper.readValue(movieResponseJson, JMovie.class);
        UUID generatedMovieId = createdMovie.getId();

        mockMvc.perform(get("/movies/" + generatedMovieId))
                .andExpect(status().is2xxSuccessful());

        // 2. Création d'une Room
        JRoom room = JRoom.builder()
                .number("Room A")
                .capacity(100)
                .build();

        String roomResponseJson = mockMvc.perform(post("/rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(room)))
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();

        JRoom createdRoom = objectMapper.readValue(roomResponseJson, JRoom.class);
        UUID generatedRoomId = createdRoom.getId();

        mockMvc.perform(get("/rooms/" + generatedRoomId))
                .andExpect(status().is2xxSuccessful());

        // 3. Création d'un Seat (corrigé)
        JSeat seat = JSeat.builder()
                .number("A1")
                .room(createdRoom) // Association avec la salle créée
                .build();

        String seatResponseJson = mockMvc.perform(post("/seats")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(seat)))
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();

        JSeat createdSeat = objectMapper.readValue(seatResponseJson, JSeat.class);
        UUID generatedSeatId = createdSeat.getId();

        mockMvc.perform(get("/seats/" + generatedSeatId))
                .andExpect(status().is2xxSuccessful());

        // 4. Création d'une Projection (corrigé)
        // On utilise les IDs récupérés pour associer la projection
        JProjection projection = JProjection.builder()
                .datetime(Instant.now())
                .movie(createdMovie) // ← Ajout : association avec le Movie
                .room(createdRoom)   // ← Ajout : association avec la Room
                .seatPrice(java.math.BigDecimal.valueOf(10.50)) // ← Ajout : prix requis
                .build();

        String projectionResponseJson = mockMvc.perform(post("/projections")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projection)))
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();

        JProjection createdProjection = objectMapper.readValue(projectionResponseJson, JProjection.class);
        UUID generatedProjectionId = createdProjection.getId();

        mockMvc.perform(get("/projections/" + generatedProjectionId))
                .andExpect(status().is2xxSuccessful());
    }
}