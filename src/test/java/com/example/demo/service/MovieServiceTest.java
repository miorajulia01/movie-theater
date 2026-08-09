package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.demo.entity.JMovie;
import com.example.demo.repository.JMovieRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MovieServiceTest {

  private JMovieRepository movieRepository;
  private MovieService movieService;

  @BeforeEach
  void setUp() {
    movieRepository = mock(JMovieRepository.class);
    movieService = new MovieService(movieRepository);
  }

  @Test
  void testFindAll() {
    JMovie movie = new JMovie();
    when(movieRepository.findAll()).thenReturn(List.of(movie));

    List<JMovie> movies = movieService.findAll();
    assertEquals(1, movies.size());
    verify(movieRepository, times(1)).findAll();
  }

  @Test
  void testFindByIdFound() {
    UUID id = UUID.randomUUID();
    JMovie movie = new JMovie();
    movie.setId(id);
    when(movieRepository.findById(id)).thenReturn(Optional.of(movie));

    JMovie found = movieService.findById(id);
    assertNotNull(found);
    assertEquals(id, found.getId());
  }

  @Test
  void testFindByIdNotFound() {
    UUID id = UUID.randomUUID();
    when(movieRepository.findById(id)).thenReturn(Optional.empty());

    assertThrows(RuntimeException.class, () -> movieService.findById(id));
  }

  @Test
  void testSave() {
    JMovie movie = new JMovie();
    when(movieRepository.save(any(JMovie.class))).thenReturn(movie);

    JMovie saved = movieService.save(movie);
    assertNotNull(saved);
    verify(movieRepository, times(1)).save(movie);
  }
}
