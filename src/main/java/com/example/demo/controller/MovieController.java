package com.example.demo.controller;

import com.example.demo.dto.request.MovieRequest;
import com.example.demo.dto.response.MovieResponse;
import com.example.demo.service.MovieService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {
  private final MovieService movieService;

  @GetMapping
  public ResponseEntity<List<MovieResponse>> findAll() {
    return ResponseEntity.ok(movieService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<MovieResponse> getById(@PathVariable UUID id) {
    return ResponseEntity.ok(movieService.findById(id));
  }

  @PostMapping
  public ResponseEntity<MovieResponse> create(@RequestBody MovieRequest request) {
    return ResponseEntity.ok(movieService.save(request));
  }
}
