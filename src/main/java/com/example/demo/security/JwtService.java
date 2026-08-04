package com.example.demo.security;

import org.springframework.stereotype.Service;

import java.security.Key;

@Service
public class JwtService {

	private final Key key;
	private final long expirationMilis;

}
