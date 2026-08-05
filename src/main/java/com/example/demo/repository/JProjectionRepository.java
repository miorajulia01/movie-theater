package com.example.demo.repository;

import com.example.demo.entity.JProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JProjectionRepository extends JpaRepository<JProjection, UUID> {
}