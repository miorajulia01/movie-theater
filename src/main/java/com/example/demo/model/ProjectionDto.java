package com.example.demo.model;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectionDto {
  private String id;
  private String movieId;
  private String roomId;
  private Instant startTime;
}
