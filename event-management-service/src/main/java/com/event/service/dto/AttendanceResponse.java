package com.event.service.dto;

import com.event.service.enums.AttendanceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceResponse {
    private String eventId;
    private String userId;
    private AttendanceType status;
    private LocalDateTime respondedAt;
}
