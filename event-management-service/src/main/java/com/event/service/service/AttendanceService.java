package com.event.service.service;

import com.event.service.dto.AttendanceResponse;
import com.event.service.dto.CreateAttendanceRequest;
import com.event.service.dto.UpdateAttendanceRequest;

import java.util.List;

public interface AttendanceService {
    AttendanceResponse createAttendance(CreateAttendanceRequest req);
    List<AttendanceResponse> getByEventId(String eventId);
    List<AttendanceResponse> getByUserId(String userId);
    AttendanceResponse updateAttendance(String eventId, UpdateAttendanceRequest req);
    void deleteAttendance(String eventId);
}
