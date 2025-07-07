package com.event.service.dto;

import com.event.service.enums.EventVisibilityType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEventRequest {
    @NotBlank @Size(min=10, max=100)
    private String title;

    @NotBlank @Size(min=10, max=1000)
    private String description;

    @NotNull
    private LocalDateTime startTime;

    @NotNull
    private LocalDateTime endTime;

    @NotBlank
    private String location;

    @NotNull
    private EventVisibilityType visibility;
}
