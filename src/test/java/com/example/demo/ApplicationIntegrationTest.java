package com.example.demo;

import com.example.demo.conf.FacadeIT;
import com.example.demo.entity.JMovie;
import com.example.demo.entity.JProjection;
import com.example.demo.entity.JRoom;
import com.example.demo.entity.JSeat;
import com.example.demo.enums.Genre;
import com.fasterxml.jackson.databind.ObjectMapper;
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
class ApplicationIntegrationTest extends FacadeIT {

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

        // =========================================================
        // 1. MOVIE
        // =========================================================

        JMovie movie = JMovie.builder()
                .title("Inception")
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


        // =========================================================
        // 2. ROOM
        // =========================================================

        JRoom room = JRoom.builder()
                .number("Room A")
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


        // =========================================================
        // 3. SEAT
        // =========================================================

        /*
         * IMPORTANT :
         * JSeat.room possède @JsonIgnore.
         *
         * On ne peut donc pas utiliser :
         *
         * objectMapper.writeValueAsString(seat)
         *
         * car "room" serait supprimé du JSON.
         *
         * On envoie donc explicitement l'ID de la Room.
         */

        String seatJson = """
                {
                    "number": "A1",
                    "room": {
                        "id": "%s"
                    }
                }
                """.formatted(generatedRoomId);

        String seatResponseJson = mockMvc.perform(
                        post("/seats")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(seatJson)
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


        // =========================================================
        // 4. PROJECTION
        // =========================================================

        /*
         * Même principe :
         * on possède déjà les IDs du Movie et de la Room.
         *
         * On évite d'envoyer les objets complets.
         */

        String projectionJson = """
                {
                    "datetime": "%s",
                    "seatPrice": 10.50,
                    "movie": {
                        "id": "%s"
                    },
                    "room": {
                        "id": "%s"
                    }
                }
                """.formatted(
                Instant.now(),
                generatedMovieId,
                generatedRoomId
        );

        String projectionResponseJson = mockMvc.perform(
                        post("/projections")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(projectionJson)
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