package com.event.service.controller;

import com.event.service.dto.CreateEventRequest;
import com.event.service.dto.EventResponse;
import com.event.service.dto.UpdateEventRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.event.service.service.EventService;

import java.util.List;

@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping("/add")
    public ResponseEntity<String> create(
            @Valid @RequestBody CreateEventRequest req
    ) {
        String id = eventService.createEvent(
                req.getTitle(),
                req.getDescription(),
                req.getHostId(),
                req.getStartTime(),
                req.getEndTime(),
                req.getLocation(),
                req.getVisibility()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @GetMapping("/all")
    public ResponseEntity<List<EventResponse>> list() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getById(@PathVariable String id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventResponse> update(
            @PathVariable String id,
            @Valid @RequestBody UpdateEventRequest req
    ) {
        return ResponseEntity.ok(eventService.updateEvent(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

}
