package com.example.demo.controller;

import com.example.demo.entity.JMovie;
import com.example.demo.service.MovieService;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies")
@AllArgsConstructor
public class MovieController {
  private final MovieService movieService;

  @GetMapping
  public List<JMovie> getAllMovies() {
    return movieService.findAll();
  }

  @GetMapping("/{id}")
  public JMovie getMovieById(@PathVariable UUID id) {
    return movieService.findById(id);
  }

  @PostMapping
  public JMovie createMovie(@RequestBody JMovie movie) {
    return movieService.save(movie);
  }
}
