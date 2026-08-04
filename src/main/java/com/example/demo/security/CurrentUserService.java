package com.example.demo.security;

import com.example.demo.entity.User;
import com.example.demo.exception.UnauthorizedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserService {

	public User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null
		|| !authentication.isAuthenticated()
			|| !(authentication.getPrincipal() instanceof User user)) {
			throw new UnauthorizedException("No authenticated user found");
		}

		return user;
	}
}
