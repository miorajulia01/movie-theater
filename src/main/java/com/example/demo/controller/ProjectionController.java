package com.example.demo.controller;

import com.example.demo.dto.request.ProjectionRequest;
import com.example.demo.dto.response.ProjectionResponse;
import com.example.demo.service.ProjectionService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projections")
@RequiredArgsConstructor
public class ProjectionController {
  private final ProjectionService projectionService;

  @GetMapping
  public ResponseEntity<List<ProjectionResponse>> findAll() {
    return ResponseEntity.ok(projectionService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProjectionResponse> getById(@PathVariable UUID id) {
    return ResponseEntity.ok(projectionService.findById(id));
  }

  @PostMapping
  public ResponseEntity<ProjectionResponse> create(@RequestBody ProjectionRequest request) {
    return ResponseEntity.ok(projectionService.save(request));
  }
}
