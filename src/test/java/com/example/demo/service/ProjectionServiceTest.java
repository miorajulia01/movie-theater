package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.demo.dto.request.ProjectionRequest;
import com.example.demo.dto.response.ProjectionResponse;
import com.example.demo.entity.JMovie;
import com.example.demo.entity.JProjection;
import com.example.demo.entity.JRoom;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.ProjectionMapper;
import com.example.demo.repository.JMovieRepository;
import com.example.demo.repository.JProjectionRepository;
import com.example.demo.repository.JRoomRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProjectionServiceTest {

  private JProjectionRepository projectionRepository;
  private JMovieRepository movieRepository;
  private JRoomRepository roomRepository;
  private ProjectionMapper projectionMapper;
  private ProjectionService projectionService;

  @BeforeEach
  void setUp() {
    projectionRepository = mock(JProjectionRepository.class);
    movieRepository = mock(JMovieRepository.class);
    roomRepository = mock(JRoomRepository.class);
    projectionMapper = mock(ProjectionMapper.class);
    projectionService =
        new ProjectionService(
            projectionRepository, movieRepository, roomRepository, projectionMapper);
  }

  @Test
  void testFindAll() {
    JProjection projection = new JProjection();
    ProjectionResponse response = new ProjectionResponse();

    when(projectionRepository.findAll()).thenReturn(List.of(projection));
    when(projectionMapper.toResponse(projection)).thenReturn(response);

    List<ProjectionResponse> projections = projectionService.findAll();
    assertEquals(1, projections.size());
    verify(projectionRepository, times(1)).findAll();
  }

  @Test
  void testFindByIdFound() {
    UUID id = UUID.randomUUID();
    JProjection projection = new JProjection();
    ProjectionResponse response = new ProjectionResponse();

    when(projectionRepository.findById(id)).thenReturn(Optional.of(projection));
    when(projectionMapper.toResponse(projection)).thenReturn(response);

    ProjectionResponse found = projectionService.findById(id);
    assertNotNull(found);
    verify(projectionRepository, times(1)).findById(id);
  }

  @Test
  void testFindByIdNotFound() {
    UUID id = UUID.randomUUID();
    when(projectionRepository.findById(id)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> projectionService.findById(id));
  }

  @Test
  void testSave() {
    UUID movieId = UUID.randomUUID();
    UUID roomId = UUID.randomUUID();
    ProjectionRequest request = new ProjectionRequest();
    request.setMovieId(movieId);
    request.setRoomId(roomId);

    JMovie movie = new JMovie();
    JRoom room = new JRoom();
    JProjection projection = new JProjection();
    JProjection savedProjection = new JProjection();
    ProjectionResponse response = new ProjectionResponse();

    when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
    when(roomRepository.findById(roomId)).thenReturn(Optional.of(room));
    when(projectionMapper.toEntity(request, movie, room)).thenReturn(projection);
    when(projectionRepository.save(projection)).thenReturn(savedProjection);
    when(projectionMapper.toResponse(savedProjection)).thenReturn(response);

    ProjectionResponse saved = projectionService.save(request);
    assertNotNull(saved);
    verify(projectionRepository, times(1)).save(projection);
  }
}
