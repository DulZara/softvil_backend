package com.event.service.repository;

import com.event.service.domain.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, String > {
    List<Attendance> findByEventId(String eventId);
    List<Attendance> findByUserId(String userId);
}
