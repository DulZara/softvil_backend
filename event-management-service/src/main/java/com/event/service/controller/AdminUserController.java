package com.event.service.controller;

import com.event.service.dto.AdminCreateRequest;
import com.event.service.dto.ChangeRoleRequest;
import com.event.service.dto.CreateUserRequest;
import com.event.service.dto.UserResponse;
import com.event.service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
@Validated
public class AdminUserController {
    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody @Valid AdminCreateRequest req) {
        userService.createUser(
                req.getName(),
                req.getEmail(),
                req.getPassword(),
                req.getRole().name()
        );
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> listAll() {
        return ResponseEntity.ok(userService.listAllUsers());
    }

    @PutMapping("/{id}/role")
    public ResponseEntity<Void> changeRole(
            @PathVariable("id") String userId,
            @RequestBody ChangeRoleRequest req
    ) {
        userService.changeUserRole(userId, req.getRole());
        return ResponseEntity.noContent().build();
    }
}
