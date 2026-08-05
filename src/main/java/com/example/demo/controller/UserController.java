package com.example.demo.controller;

import com.example.demo.entity.JUser;
import com.example.demo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<JUser> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public JUser getUserById(@PathVariable UUID id) {
        return userService.findById(id);
    }

    // à faire la partie de sping security
}