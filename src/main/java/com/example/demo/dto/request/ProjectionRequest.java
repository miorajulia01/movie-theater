// package com.example.demo.dto.request;
package com.example.demo.dto.request;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProjectionRequest {
    @NotNull private Instant datetime;
    @NotNull private BigDecimal seatPrice;
    @NotNull private UUID movieId;
    @NotNull private UUID roomId;
}