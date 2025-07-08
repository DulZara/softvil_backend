package com.event.service.service;

import com.event.service.dto.RegisterRequest;
import com.event.service.dto.UserResponse;
import com.event.service.enums.UserRoleType;

import java.util.List;

public interface UserService {
    UserResponse toUserResponse(String userId);
    void createUser(String name, String email, String rawPassword, String roleString);
    void register(RegisterRequest req);
    List<UserResponse> listAllUsers();
    void changeUserRole(String userId, UserRoleType newRole);


}
