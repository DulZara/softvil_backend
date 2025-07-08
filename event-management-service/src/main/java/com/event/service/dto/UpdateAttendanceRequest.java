package com.event.service.dto;

import com.event.service.enums.AttendanceType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAttendanceRequest {
    @NotNull
    private AttendanceType status;
}
