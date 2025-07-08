package com.event.service.repository;

import com.event.service.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String > {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

}
