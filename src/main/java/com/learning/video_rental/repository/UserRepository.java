package com.learning.video_rental.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learning.video_rental.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
