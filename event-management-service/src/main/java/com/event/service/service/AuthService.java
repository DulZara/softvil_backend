package com.event.service.service;

import com.event.service.dto.JwtResponse;
import com.event.service.dto.LoginRequest;
import com.event.service.dto.RegisterRequest;

public interface AuthService {
    void register(RegisterRequest req);
    JwtResponse login(LoginRequest req);
}
