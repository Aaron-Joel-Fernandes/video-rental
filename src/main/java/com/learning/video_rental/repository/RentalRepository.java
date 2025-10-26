package com.learning.video_rental.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learning.video_rental.entity.Rental;
import com.learning.video_rental.entity.User;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findByUserAndReturnedAtIsNull(User user);
    List<Rental> findByUser(User user);
}
