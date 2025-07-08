package com.event.service.service.impl;

import com.event.service.config.JwtTokenProvider;
import com.event.service.domain.User;
import com.event.service.dto.JwtResponse;
import com.event.service.dto.LoginRequest;
import com.event.service.dto.RegisterRequest;
import com.event.service.enums.UserRoleType;
import com.event.service.exception.ApplicationException;
import com.event.service.repository.UserRepository;
import com.event.service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepo;
    private final AuthenticationManager authManager;
    private final JwtTokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(RegisterRequest req) {
        if (userRepo.findByEmail(req.getEmail()).isPresent()) {
            throw new ApplicationException("100002", "error.email.exists");
        }
        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .name(req.getName())
                .email(req.getEmail())
                .passwordHash(passwordEncoder.encode(req.getPassword()))
                .role(UserRoleType.USER)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        userRepo.save(user);
    }

    @Override
    public JwtResponse login(LoginRequest req) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
        );
        String userId = ((org.springframework.security.core.userdetails.User) auth.getPrincipal())
                .getUsername(); // here principal.username == email
        String token = tokenProvider.generateToken(userId);
        return new JwtResponse(token, "Bearer");
    }
}
