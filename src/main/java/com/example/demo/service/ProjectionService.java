package com.example.demo.service;

import com.example.demo.dto.request.ProjectionRequest;
import com.example.demo.dto.response.ProjectionResponse;
import com.example.demo.entity.*;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.ProjectionMapper;
import com.example.demo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectionService {
  private final JProjectionRepository projectionRepository;
  private final JMovieRepository movieRepository;
  private final JRoomRepository roomRepository;
  private final ProjectionMapper projectionMapper;

  public ProjectionResponse findById(UUID id) {
    JProjection projection = projectionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Projection not found: " + id));
    return projectionMapper.toResponse(projection);
  }

  public ProjectionResponse save(ProjectionRequest request) {
    JMovie movie = movieRepository.findById(request.getMovieId()).orElseThrow();
    JRoom room = roomRepository.findById(request.getRoomId()).orElseThrow();
    return projectionMapper.toResponse(projectionRepository.save(projectionMapper.toEntity(request, movie, room)));
  }
}