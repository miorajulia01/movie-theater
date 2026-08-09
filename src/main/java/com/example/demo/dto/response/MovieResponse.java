package com.example.demo.dto.response;

import com.example.demo.enums.Genre;
import java.time.Duration;
import java.util.Set;
import java.util.UUID;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieResponse {
  private UUID id;
  private String title;
  private Set<Genre> genre;
  private String description;
  private Duration duration;
}
