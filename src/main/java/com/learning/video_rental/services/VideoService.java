package com.learning.video_rental.services;

import com.learning.video_rental.entity.Video;
import com.learning.video_rental.repository.VideoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class VideoService {
    private final VideoRepository repo;
    public VideoService(VideoRepository repo) { this.repo = repo; }
    public List<Video> getAll() { return repo.findAll(); }
    public List<Video> getAvailable() { return repo.findByAvailableTrue(); }
    public Optional<Video> getById(Long id) { return repo.findById(id); }
    public Video create(Video v) { return repo.save(v); }
    public Video update(Video v) { return repo.save(v); }
    public void delete(Long id) { repo.deleteById(id); }
}
