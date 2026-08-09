package com.example.demo.service;

import com.example.demo.entity.JProjection;
import com.example.demo.repository.JProjectionRepository;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProjectionService {
  private final JProjectionRepository projectionRepository;

  public List<JProjection> findAll() {
    return projectionRepository.findAll();
  }

  public JProjection findById(UUID id) {
    return projectionRepository
        .findById(id)
        .orElseThrow(() -> new RuntimeException("Projection not found"));
  }

  public JProjection save(JProjection projection) {
    return projectionRepository.save(projection);
  }
}
