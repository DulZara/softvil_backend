package com.event.service.service.impl;

import com.event.service.domain.User;
import com.event.service.dto.RegisterRequest;
import com.event.service.dto.UserResponse;
import com.event.service.enums.UserRoleType;
import com.event.service.exception.ApplicationException;
import com.event.service.repository.UserRepository;
import com.event.service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserResponse toUserResponse(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ApplicationException("100004","error.user.notfound")
                );
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }


    @Override
    public void register(RegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new ApplicationException("100002", "error.user.exists");
        }
        User u = User.builder()
                .name(req.getName())
                .email(req.getEmail())
                .passwordHash(passwordEncoder.encode(req.getPassword()))
                .role(UserRoleType.USER)                      // ← default
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        userRepository.save(u);
    }




    @Override
    public void createUser(String name,
                           String email,
                           String rawPassword,
                           String roleString) {
        if (userRepository.existsByEmail(email)) {
            throw new ApplicationException("100002", "error.user.exists");
        }
        UserRoleType role;
        try {
            role = UserRoleType.valueOf(roleString.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ApplicationException("100005", "error.invalid.role");
        }
        User user = User.builder()
                .name(name)
                .email(email)
                .passwordHash(passwordEncoder.encode(rawPassword))
                .role(role)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        userRepository.save(user);
    }

    @Override
    public List<UserResponse> listAllUsers() {
        return userRepository.findAll().stream()
                .map(u -> new UserResponse(
                        u.getId(),
                        u.getName(),
                        u.getEmail(),
                        u.getRole()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void changeUserRole(String userId, UserRoleType newRole) {
        User u = userRepository.findById(userId)
                .orElseThrow(() -> new ApplicationException("300001", "error.user.notfound"));
        u.setRole(newRole);
        userRepository.save(u);
    }

}
