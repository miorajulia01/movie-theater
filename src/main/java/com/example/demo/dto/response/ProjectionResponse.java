// package com.example.demo.dto.response;
package com.example.demo.dto.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProjectionResponse {
    private UUID id;
    private Instant datetime;
    private BigDecimal seatPrice;
    private MovieResponse movie;
    private RoomResponse room;
}