// package com.example.demo.dto.request;
package com.example.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SeatRequest {
    @NotBlank private String number;
    @NotNull private UUID roomId;
}