package com.event.service.dto;

import com.event.service.enums.UserRoleType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @NotBlank @Size(min = 3, max = 100)
    private String name;

    @Email @NotBlank
    private String email;

    @NotBlank @Size(min = 8, max = 100)
    private String password;


}
