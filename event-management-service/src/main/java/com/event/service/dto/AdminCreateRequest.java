package com.event.service.dto;

import com.event.service.enums.UserRoleType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminCreateRequest {
    @NotBlank
    private String name;
    @Email @NotBlank private String email;
    @NotBlank private String password;
    @NotNull  private UserRoleType role;
}
