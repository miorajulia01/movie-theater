package com.example.demo.service;

import com.example.demo.entity.JMovie;
import com.example.demo.repository.JMovieRepository;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MovieService {
  private final JMovieRepository movieRepository;

  public List<JMovie> findAll() {
    return movieRepository.findAll();
  }

  public JMovie findById(UUID id) {
    return movieRepository.findById(id).orElseThrow(() -> new RuntimeException("Movie not found"));
  }

  public JMovie save(JMovie movie) {
    return movieRepository.save(movie);
  }
}
