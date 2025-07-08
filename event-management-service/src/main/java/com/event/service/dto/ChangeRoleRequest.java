package com.event.service.dto;

import com.event.service.enums.UserRoleType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChangeRoleRequest {
    @NotNull
    private UserRoleType role;
}
