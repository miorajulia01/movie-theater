package com.example.demo.service;

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
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectionService {
  private final JProjectionRepository projectionRepository;
  private final JMovieRepository movieRepository;
  private final JRoomRepository roomRepository;
  private final ProjectionMapper projectionMapper;

  public List<ProjectionResponse> findAll() {
    return projectionRepository.findAll().stream()
        .map(projectionMapper::toResponse)
        .collect(Collectors.toList());
  }

  public ProjectionResponse findById(UUID id) {
    JProjection projection =
        projectionRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Projection not found: " + id));
    return projectionMapper.toResponse(projection);
  }

  public ProjectionResponse save(ProjectionRequest request) {
    JMovie movie =
        movieRepository
            .findById(request.getMovieId())
            .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
    JRoom room =
        roomRepository
            .findById(request.getRoomId())
            .orElseThrow(() -> new ResourceNotFoundException("Room not found"));
    JProjection projection = projectionMapper.toEntity(request, movie, room);
    JProjection saved = projectionRepository.save(projection);
    return projectionMapper.toResponse(saved);
  }
}
