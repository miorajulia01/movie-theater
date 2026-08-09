// package com.example.demo.dto.request;
package com.example.demo.dto.request;

import com.example.demo.enums.Genre;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import java.util.Set;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieRequest {
  @NotBlank private String title;
  @NotNull private Set<Genre> genre;
  private String description;
  @NotNull private Duration duration;
}
