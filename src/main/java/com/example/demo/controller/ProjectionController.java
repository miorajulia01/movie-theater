package com.example.demo.controller;

import com.example.demo.entity.JProjection;
import com.example.demo.service.ProjectionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/projections")
@AllArgsConstructor
public class ProjectionController {
    private final ProjectionService projectionService;

    @GetMapping
    public List<JProjection> getAllProjections() {
        return projectionService.findAll();
    }

    @GetMapping("/{id}")
    public JProjection getProjectionById(@PathVariable UUID id) {
        return projectionService.findById(id);
    }

    @PostMapping
    public JProjection createProjection(@RequestBody JProjection projection) {
        return projectionService.save(projection);
    }
}