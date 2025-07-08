package com.event.service.dto;

import com.event.service.enums.AttendanceType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAttendanceRequest {
    @NotBlank
    private String eventId;
    @NotBlank
    private String userId;
    @NotNull
    private AttendanceType status;
}
