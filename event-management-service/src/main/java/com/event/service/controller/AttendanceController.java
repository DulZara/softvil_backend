package com.event.service.controller;

import com.event.service.dto.AttendanceResponse;
import com.event.service.dto.CreateAttendanceRequest;
import com.event.service.dto.UpdateAttendanceRequest;
import com.event.service.service.AttendanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping
    public ResponseEntity<AttendanceResponse> respond(
            @Valid @RequestBody CreateAttendanceRequest req) {
        return ResponseEntity.ok(attendanceService.createAttendance(req));
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<AttendanceResponse>> byEvent(
            @PathVariable String eventId) {
        return ResponseEntity.ok(attendanceService.getByEventId(eventId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AttendanceResponse>> byUser(
            @PathVariable String userId) {
        return ResponseEntity.ok(attendanceService.getByUserId(userId));
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<AttendanceResponse> update(
            @PathVariable String eventId,
            @Valid @RequestBody UpdateAttendanceRequest req) {
        return ResponseEntity.ok(attendanceService.updateAttendance(eventId, req));
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> delete(@PathVariable String eventId) {
        attendanceService.deleteAttendance(eventId);
        return ResponseEntity.ok().build();
    }
}
