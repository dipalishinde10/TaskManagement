package com.TaskManagement.Service;

import java.util.Optional;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TaskManagement.DTO.AuthenticationResponseDTO;
import com.TaskManagement.DTO.LoginRequestDTO;
import com.TaskManagement.DTO.RegisterRequestDTO;
import com.TaskManagement.Entity.User;
import com.TaskManagement.Repository.UserRepository;
import com.TaskManagement.Security.JWTUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	
	
	public AuthenticationResponseDTO register(RegisterRequestDTO request) {
		
		Optional<User>existing = userRepo.findByUserEmail(request.getUserEmail());
		if(existing.isPresent()) {
			throw new RuntimeException("User already exist");
		}
		
		User user = new User();
		user.setUserName(request.getUsername());
		user.setUserEmail(request.getUserEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRole(request.getRole());
		
		userRepo.save(user);
		String token = jwtUtil.generateToken(user.getUserEmail());
		
		return new AuthenticationResponseDTO(token,"User register succesfully");
	}
	
	
	public String login(LoginRequestDTO dto) {
		
		User user = userRepo.findByUserEmail(dto.getUserEmail()).orElseThrow(()-> new RuntimeException("User not found"));
		
		if(!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
			throw new RuntimeException("Invalid credentials");
		}
		
		return jwtUtil.generateToken(user.getUserEmail());
		
	}
}
