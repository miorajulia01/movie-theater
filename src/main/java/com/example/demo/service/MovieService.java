package com.example.demo.service;

import com.example.demo.dto.request.MovieRequest;
import com.example.demo.dto.response.MovieResponse;
import com.example.demo.entity.JMovie;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.MovieMapper;
import com.example.demo.repository.JMovieRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieService {

  private final JMovieRepository movieRepository;
  private final MovieMapper movieMapper;

  public MovieResponse findById(UUID id) {
    JMovie movie =
        movieRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id: " + id));
    return movieMapper.toResponse(movie);
  }

  public List<MovieResponse> findAll() {
    return movieRepository.findAll().stream()
        .map(movieMapper::toResponse)
        .collect(Collectors.toList());
  }

  public MovieResponse save(MovieRequest request) {
    JMovie movie = movieMapper.toEntity(request);
    JMovie savedMovie = movieRepository.save(movie);
    return movieMapper.toResponse(savedMovie);
  }
}
