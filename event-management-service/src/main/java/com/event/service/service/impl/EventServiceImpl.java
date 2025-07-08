package com.event.service.service.impl;

import com.event.service.domain.Event;
import com.event.service.dto.CreateEventRequest;
import com.event.service.dto.EventResponse;
import com.event.service.dto.UpdateEventRequest;
import com.event.service.enums.EventVisibilityType;
import com.event.service.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.event.service.repository.EventRepository;
import com.event.service.service.EventService;
import com.event.service.util.EventUtil;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    private boolean currentUserIsAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));
    }

    @Override
    public String createEvent(String title,
                              String description,
                              String hostId,
                              LocalDateTime startTime,
                              LocalDateTime endTime,
                              String location,
                              EventVisibilityType visibility) {
        // only admin may create
        if (!currentUserIsAdmin()) {
            throw new ApplicationException("200003", "error.event.forbidden");
        }

        try {
            Event e = Event.builder()
                    .id(EventUtil.generateEventId())
                    .title(title)
                    .description(description)
                    .hostId(hostId)
                    .startTime(startTime)
                    .endTime(endTime)
                    .location(location)
                    .visibility(visibility)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            eventRepository.save(e);
            return e.getId();

        } catch (Exception ex) {
            throw new ApplicationException("100001", "error.create.event");
        }
    }

    @Override
    public List<EventResponse> getAllEvents() {
        return eventRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventResponse getEventById(String id) {
        Event e = eventRepository.findById(id)
                .orElseThrow(() -> new ApplicationException("200001", "error.event.notfound"));
        return toDto(e);
    }

    @Override
    public EventResponse updateEvent(String id, UpdateEventRequest req) {
        // only admin may update
        if (!currentUserIsAdmin()) {
            throw new ApplicationException("200003", "error.event.forbidden");
        }

        Event e = eventRepository.findById(id)
                .orElseThrow(() -> new ApplicationException("200001", "error.event.notfound"));

        e.setTitle(req.getTitle());
        e.setDescription(req.getDescription());
        e.setLocation(req.getLocation());
        e.setStartTime(req.getStartTime());
        e.setEndTime(req.getEndTime());
        e.setVisibility(req.getVisibility());
        e.setUpdatedAt(LocalDateTime.now());

        eventRepository.save(e);
        return toDto(e);
    }

    @Override
    public void deleteEvent(String id) {
        // only admin may delete
        if (!currentUserIsAdmin()) {
            throw new ApplicationException("200003", "error.event.forbidden");
        }

        if (!eventRepository.existsById(id)) {
            throw new ApplicationException("200001", "error.event.notfound");
        }
        eventRepository.deleteById(id);
    }

    private EventResponse toDto(Event e) {
        return new EventResponse(
                e.getId(),
                e.getTitle(),
                e.getDescription(),
                e.getHostId(),
                e.getStartTime(),
                e.getEndTime(),
                e.getLocation(),
                e.getVisibility(),
                e.getCreatedAt(),
                e.getUpdatedAt()
        );
    }

}
