package com.Voice.Aerius.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Voice.Aerius.Auth.model.*;;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    
}