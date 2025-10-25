package com.learning.video_rental.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learning.video_rental.entity.Video;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Long> {
    List<Video> findByAvailableTrue();
}
