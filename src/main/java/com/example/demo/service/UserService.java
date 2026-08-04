package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

	private final UserRepository userRepository;


	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return userRepository
				.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("No user found with email " + email));
	}

	public User findById(UUID id){
		return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
	}

	public User update(
			UUID id, String firstName, String lastName, LocalDate birthdate, String phone) {
		User user = findById(id);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setBirthdate(birthdate);
		user.setPhone(phone);
		return userRepository.save(user);
	}
}
