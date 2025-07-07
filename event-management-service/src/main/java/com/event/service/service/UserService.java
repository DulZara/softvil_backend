package com.event.service.service;

import com.event.service.dto.UserResponse;

public interface UserService {
    UserResponse toUserResponse(String userId);
}
