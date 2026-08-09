package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.demo.dto.request.MovieRequest;
import com.example.demo.dto.response.MovieResponse;
import com.example.demo.entity.JMovie;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.MovieMapper;
import com.example.demo.repository.JMovieRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MovieServiceTest {

  private JMovieRepository movieRepository;
  private MovieMapper movieMapper;
  private MovieService movieService;

  @BeforeEach
  void setUp() {
    movieRepository = mock(JMovieRepository.class);
    movieMapper = mock(MovieMapper.class);
    movieService = new MovieService(movieRepository, movieMapper);
  }

  @Test
  void testFindAll() {
    JMovie movie = new JMovie();
    MovieResponse response = new MovieResponse();

    when(movieRepository.findAll()).thenReturn(List.of(movie));
    when(movieMapper.toResponse(movie)).thenReturn(response);

    List<MovieResponse> movies = movieService.findAll();
    assertEquals(1, movies.size());
    verify(movieRepository, times(1)).findAll();
  }

  @Test
  void testFindByIdFound() {
    UUID id = UUID.randomUUID();
    JMovie movie = new JMovie();
    MovieResponse response = new MovieResponse();

    when(movieRepository.findById(id)).thenReturn(Optional.of(movie));
    when(movieMapper.toResponse(movie)).thenReturn(response);

    MovieResponse found = movieService.findById(id);
    assertNotNull(found);
    verify(movieRepository, times(1)).findById(id);
  }

  @Test
  void testFindByIdNotFound() {
    UUID id = UUID.randomUUID();
    when(movieRepository.findById(id)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> movieService.findById(id));
  }

  @Test
  void testSave() {
    MovieRequest request = new MovieRequest();
    JMovie movie = new JMovie();
    JMovie savedMovie = new JMovie();
    MovieResponse response = new MovieResponse();

    when(movieMapper.toEntity(request)).thenReturn(movie);
    when(movieRepository.save(movie)).thenReturn(savedMovie);
    when(movieMapper.toResponse(savedMovie)).thenReturn(response);

    MovieResponse saved = movieService.save(request);
    assertNotNull(saved);
    verify(movieRepository, times(1)).save(movie);
  }
}
