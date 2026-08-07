package com.example.demo;

import com.example.demo.entity.*;
import com.example.demo.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldTestAllEndpoints() {
        // 1. User
        UUID userId = UUID.randomUUID();
        JUser user = JUser.builder()
                .id(userId)
                .firstName("John")
                .lastName("Doe")
                .birthdate(LocalDate.of(2000, 1, 1))
                .email("john@example.com")
                .password("secret")
                .phone("0340000000")
                .role(Role.CLIENT)
                .build();
        ResponseEntity<JUser> userResponse = restTemplate.postForEntity("/users", user, JUser.class);
        assertThat(userResponse.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(restTemplate.getForEntity("/users/" + userId, JUser.class).getStatusCode().is2xxSuccessful()).isTrue();

        // 2. Movie
        UUID movieId = UUID.randomUUID();
        JMovie movie = JMovie.builder()
                .id(movieId)
                .title("Inception")
                .description("Sci-fi thriller")
                .build();
        ResponseEntity<JMovie> movieResponse = restTemplate.postForEntity("/movies", movie, JMovie.class);
        assertThat(movieResponse.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(restTemplate.getForEntity("/movies/" + movieId, JMovie.class).getStatusCode().is2xxSuccessful()).isTrue();

        // 3. Room
        UUID roomId = UUID.randomUUID();
        JRoom room = JRoom.builder()
                .id(roomId)
                .number("Room A")
                .capacity(100)
                .build();
        ResponseEntity<JRoom> roomResponse = restTemplate.postForEntity("/rooms", room, JRoom.class);
        assertThat(roomResponse.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(restTemplate.getForEntity("/rooms/" + roomId, JRoom.class).getStatusCode().is2xxSuccessful()).isTrue();

        // 4. Seat
        UUID seatId = UUID.randomUUID();
        JSeat seat = JSeat.builder()
                .id(seatId)
                .number("A1")
                .build();
        ResponseEntity<JSeat> seatResponse = restTemplate.postForEntity("/seats", seat, JSeat.class);
        assertThat(seatResponse.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(restTemplate.getForEntity("/seats/" + seatId, JSeat.class).getStatusCode().is2xxSuccessful()).isTrue();

        // 5. Projection
        UUID projectionId = UUID.randomUUID();
        JProjection projection = JProjection.builder()
                .id(projectionId)
                .datetime(Instant.now())
                .build();
        ResponseEntity<JProjection> projectionResponse = restTemplate.postForEntity("/projections", projection, JProjection.class);
        assertThat(projectionResponse.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(restTemplate.getForEntity("/projections/" + projectionId, JProjection.class).getStatusCode().is2xxSuccessful()).isTrue();

        // 6. Reservation
        UUID reservationId = UUID.randomUUID();
        JReservation reservation = JReservation.builder()
                .id(reservationId)
                .createdAt(Instant.now())
                .build();
        ResponseEntity<JReservation> reservationResponse = restTemplate.postForEntity("/reservations", reservation, JReservation.class);
        assertThat(reservationResponse.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(restTemplate.getForEntity("/reservations/" + reservationId, JReservation.class).getStatusCode().is2xxSuccessful()).isTrue();
    }
}