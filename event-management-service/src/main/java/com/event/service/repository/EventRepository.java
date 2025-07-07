package com.event.service.repository;

import com.event.service.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EventRepository extends JpaRepository<Event, String> {
}
