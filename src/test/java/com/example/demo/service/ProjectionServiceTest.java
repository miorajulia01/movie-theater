package com.example.demo.service;

import com.example.demo.entity.JProjection;
import com.example.demo.repository.JProjectionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProjectionServiceTest {

    private JProjectionRepository projectionRepository;
    private ProjectionService projectionService;

    @BeforeEach
    void setUp() {
        projectionRepository = mock(JProjectionRepository.class);
        projectionService = new ProjectionService(projectionRepository);
    }

    @Test
    void testFindAll() {
        JProjection projection = new JProjection();
        when(projectionRepository.findAll()).thenReturn(List.of(projection));

        List<JProjection> projections = projectionService.findAll();
        assertEquals(1, projections.size());
        verify(projectionRepository, times(1)).findAll();
    }

    @Test
    void testFindByIdFound() {
        UUID id = UUID.randomUUID();
        JProjection projection = new JProjection();
        projection.setId(id);
        when(projectionRepository.findById(id)).thenReturn(Optional.of(projection));

        JProjection found = projectionService.findById(id);
        assertNotNull(found);
        assertEquals(id, found.getId());
    }

    @Test
    void testFindByIdNotFound() {
        UUID id = UUID.randomUUID();
        when(projectionRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> projectionService.findById(id));
    }

    @Test
    void testSave() {
        JProjection projection = new JProjection();
        when(projectionRepository.save(any(JProjection.class))).thenReturn(projection);

        JProjection saved = projectionService.save(projection);
        assertNotNull(saved);
        verify(projectionRepository, times(1)).save(projection);
    }
}