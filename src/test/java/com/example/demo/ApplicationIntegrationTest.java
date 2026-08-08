package com.example.demo;

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

import java.math.BigDecimal;
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
class ApplicationIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:15-alpine");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @TestConfiguration
    static class NoSecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http)
                throws Exception {

            http.csrf(csrf -> csrf.disable())
                    .authorizeHttpRequests(auth ->
                            auth.anyRequest().permitAll());

            return http.build();
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldTestAllEndpoints() throws Exception {

        // Movie
        JMovie movie = JMovie.builder()
                .title("Inception - " + UUID.randomUUID())
                .description("Sci-fi thriller")
                .duration(Duration.ofMinutes(148))
                .genre(Set.of(Genre.ACTION))
                .build();

        String movieResponseJson = mockMvc.perform(
                        post("/movies")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(movie))
                )
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JMovie createdMovie =
                objectMapper.readValue(movieResponseJson, JMovie.class);

        UUID generatedMovieId = createdMovie.getId();

        mockMvc.perform(
                        get("/movies/" + generatedMovieId)
                )
                .andExpect(status().is2xxSuccessful());


        // Room
        JRoom room = JRoom.builder()
                .number("Room - " + UUID.randomUUID())
                .capacity(100)
                .build();

        String roomResponseJson = mockMvc.perform(
                        post("/rooms")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(room))
                )
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JRoom createdRoom =
                objectMapper.readValue(roomResponseJson, JRoom.class);

        UUID generatedRoomId = createdRoom.getId();

        mockMvc.perform(
                        get("/rooms/" + generatedRoomId)
                )
                .andExpect(status().is2xxSuccessful());


        // Seat
        JSeat seat = JSeat.builder()
                .number("Seat - " + UUID.randomUUID())
                .room(createdRoom)
                .build();

        String seatResponseJson = mockMvc.perform(
                        post("/seats")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(seat))
                )
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JSeat createdSeat =
                objectMapper.readValue(seatResponseJson, JSeat.class);

        UUID generatedSeatId = createdSeat.getId();

        mockMvc.perform(
                        get("/seats/" + generatedSeatId)
                )
                .andExpect(status().is2xxSuccessful());


        // Projection
        JProjection projection = JProjection.builder()
                .datetime(Instant.now())
                .seatPrice(new BigDecimal("10.50"))
                .room(createdRoom)
                .movie(createdMovie)
                .build();

        String projectionResponseJson = mockMvc.perform(
                        post("/projections")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(projection))
                )
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JProjection createdProjection =
                objectMapper.readValue(
                        projectionResponseJson,
                        JProjection.class
                );

        UUID generatedProjectionId =
                createdProjection.getId();

        mockMvc.perform(
                        get("/projections/" + generatedProjectionId)
                )
                .andExpect(status().is2xxSuccessful());
    }
}