package com.example.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.dto.request.MovieRequest;
import com.example.demo.dto.request.ProjectionRequest;
import com.example.demo.dto.request.RoomRequest;
import com.example.demo.dto.request.SeatRequest;
import com.example.demo.dto.response.MovieResponse;
import com.example.demo.dto.response.ProjectionResponse;
import com.example.demo.dto.response.RoomResponse;
import com.example.demo.dto.response.SeatResponse;
import com.example.demo.enums.Genre;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
class ApplicationIntegrationTest {

  @Container
  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgres::getJdbcUrl);
    registry.add("spring.datasource.username", postgres::getUsername);
    registry.add("spring.datasource.password", postgres::getPassword);
  }

  @TestConfiguration
  static class NoSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      http.csrf(csrf -> csrf.disable())
              .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

      return http.build();
    }
  }

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Test
  void shouldTestAllEndpoints() throws Exception {

    // 1. Movie
    MovieRequest movieRequest = new MovieRequest();
    movieRequest.setTitle("Inception - " + UUID.randomUUID());
    movieRequest.setDescription("Sci-fi thriller");
    movieRequest.setDuration(Duration.ofMinutes(148));
    movieRequest.setGenre(Set.of(Genre.ACTION));

    String movieResponseJson =
            mockMvc
                    .perform(
                            post("/api/movies")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(movieRequest)))
                    .andExpect(status().is2xxSuccessful())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

    MovieResponse createdMovie = objectMapper.readValue(movieResponseJson, MovieResponse.class);
    UUID generatedMovieId = createdMovie.getId();

    mockMvc.perform(get("/api/movies/" + generatedMovieId)).andExpect(status().is2xxSuccessful());
    mockMvc.perform(get("/api/movies")).andExpect(status().is2xxSuccessful());

    // 2. Room
    RoomRequest roomRequest = new RoomRequest();
    roomRequest.setNumber("Room - " + UUID.randomUUID());
    roomRequest.setCapacity(100);

    String roomResponseJson =
            mockMvc
                    .perform(
                            post("/api/rooms")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(roomRequest)))
                    .andExpect(status().is2xxSuccessful())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

    RoomResponse createdRoom = objectMapper.readValue(roomResponseJson, RoomResponse.class);
    UUID generatedRoomId = createdRoom.getId();

    mockMvc.perform(get("/api/rooms/" + generatedRoomId)).andExpect(status().is2xxSuccessful());
    mockMvc.perform(get("/api/rooms")).andExpect(status().is2xxSuccessful());

    // 3. Seat
    SeatRequest seatRequest = new SeatRequest();
    seatRequest.setNumber("Seat - " + UUID.randomUUID());
    seatRequest.setRoomId(generatedRoomId);

    String seatResponseJson =
            mockMvc
                    .perform(
                            post("/api/seats")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(seatRequest)))
                    .andExpect(status().is2xxSuccessful())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

    SeatResponse createdSeat = objectMapper.readValue(seatResponseJson, SeatResponse.class);
    UUID generatedSeatId = createdSeat.getId();

    mockMvc.perform(get("/api/seats/" + generatedSeatId)).andExpect(status().is2xxSuccessful());
    mockMvc.perform(get("/api/seats")).andExpect(status().is2xxSuccessful());

    // 4. Projection
    ProjectionRequest projectionRequest = new ProjectionRequest();
    projectionRequest.setDatetime(Instant.now());
    projectionRequest.setSeatPrice(new BigDecimal("10.50"));
    projectionRequest.setRoomId(generatedRoomId);
    projectionRequest.setMovieId(generatedMovieId);

    String projectionResponseJson =
            mockMvc
                    .perform(
                            post("/api/projections")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(projectionRequest)))
                    .andExpect(status().is2xxSuccessful())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

    ProjectionResponse createdProjection =
            objectMapper.readValue(projectionResponseJson, ProjectionResponse.class);
    UUID generatedProjectionId = createdProjection.getId();

    mockMvc
            .perform(get("/api/projections/" + generatedProjectionId))
            .andExpect(status().is2xxSuccessful());
    mockMvc.perform(get("/api/projections")).andExpect(status().is2xxSuccessful());
  }
}