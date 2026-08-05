package com.example.demo.service;

import com.example.demo.entity.JUser;
import com.example.demo.repository.JUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {
    private final JUserRepository userRepository;

    public List<JUser> findAll() {
        return userRepository.findAll();
    }

    public JUser findById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public JUser save(JUser user) {
        return userRepository.save(user);
    }
}