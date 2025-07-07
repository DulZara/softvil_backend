package com.event.service.dto;

import com.event.service.enums.EventVisibilityType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventResponse {
    private String id;
    private String title;
    private String description;
    private String hostId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String location;
    private EventVisibilityType visibility;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
