package com.event.service.controller;

import com.event.service.dto.UserResponse;
import com.event.service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> currentUser(
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User principal
    ) {
        UserResponse resp = userService.toUserResponse(principal.getUsername());
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getById(@PathVariable String userId) {
        UserResponse resp = userService.toUserResponse(userId);
        return ResponseEntity.ok(resp);
    }
}
