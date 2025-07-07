package com.event.service.service.impl;

import com.event.service.domain.User;
import com.event.service.dto.UserResponse;
import com.event.service.exception.ApplicationException;
import com.event.service.repository.UserRepository;
import com.event.service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MessageSource messageSource;

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
                user.getRole().name()
        );
    }
}
