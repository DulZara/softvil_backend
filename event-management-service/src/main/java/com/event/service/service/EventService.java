package com.event.service.service;

import com.event.service.dto.CreateEventRequest;
import com.event.service.dto.EventResponse;
import com.event.service.dto.UpdateEventRequest;
import com.event.service.enums.EventVisibilityType;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    String createEvent(String title,
                       String description,
                       String hostId,
                       LocalDateTime startTime,
                       LocalDateTime endTime,
                       String location,
                       EventVisibilityType visibility);
    List<EventResponse> getAllEvents();

    EventResponse getEventById(String id);

    EventResponse updateEvent(String id, UpdateEventRequest req);

    void deleteEvent(String id);

}
