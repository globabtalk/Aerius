package com.Voice.Aerius.Auth.repository;

import com.Voice.Aerius.Auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {
    Optional<User> findByEmail(String email);
    Optional<User> existsByEmail(Long Email);
}
