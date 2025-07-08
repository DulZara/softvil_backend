package com.event.service.service.impl;

import com.event.service.domain.Attendance;
import com.event.service.dto.AttendanceResponse;
import com.event.service.dto.CreateAttendanceRequest;
import com.event.service.dto.UpdateAttendanceRequest;
import com.event.service.exception.ApplicationException;
import com.event.service.repository.AttendanceRepository;
import com.event.service.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository repo;

    @Override
    public AttendanceResponse createAttendance(CreateAttendanceRequest req) {
        if (repo.existsById(req.getEventId())) {
            throw new ApplicationException("300001", "error.attendance.exists");
        }
        Attendance a = new Attendance();
        a.setEventId(req.getEventId());
        a.setUserId(req.getUserId());
        a.setStatus(req.getStatus());
        a.setRespondedAt(LocalDateTime.now());
        repo.save(a);
        return toDto(a);
    }

    @Override
    public List<AttendanceResponse> getByEventId(String eventId) {
        return repo.findByEventId(eventId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AttendanceResponse> getByUserId(String userId) {
        return repo.findByUserId(userId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AttendanceResponse updateAttendance(String eventId, UpdateAttendanceRequest req) {
        Attendance a = repo.findById(eventId)
                .orElseThrow(() -> new ApplicationException("300002","error.attendance.notfound"));
        a.setStatus(req.getStatus());
        a.setRespondedAt(LocalDateTime.now());
        repo.save(a);
        return toDto(a);
    }

    @Override
    public void deleteAttendance(String eventId) {
        if (!repo.existsById(eventId)) {
            throw new ApplicationException("300002","error.attendance.notfound");
        }
        repo.deleteById(eventId);
    }

    private AttendanceResponse toDto(Attendance a) {
        return new AttendanceResponse(
                a.getEventId(),
                a.getUserId(),
                a.getStatus(),
                a.getRespondedAt()
        );
    }
}
