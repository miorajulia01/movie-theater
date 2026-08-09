// package com.example.demo.dto.response;
package com.example.demo.dto.response;

import java.util.UUID;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RoomResponse {
    private UUID id;
    private String number;
    private int capacity;
}