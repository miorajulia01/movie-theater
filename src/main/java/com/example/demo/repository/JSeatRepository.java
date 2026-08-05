package com.example.demo.repository;

import com.example.demo.entity.JSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface JSeatRepository extends JpaRepository<JSeat, UUID> {
}