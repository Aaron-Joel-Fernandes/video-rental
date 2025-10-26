package com.learning.video_rental.controller;

import com.learning.video_rental.entity.Video;
import com.learning.video_rental.exception.ResourceNotFoundException;
import com.learning.video_rental.services.VideoService;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/videos")
public class VideoController {

    private final VideoService svc;
    public VideoController(VideoService svc) { this.svc = svc; }

    @GetMapping("/available")
    public ResponseEntity<List<Video>> available() { return ResponseEntity.ok(svc.getAvailable()); }

    @GetMapping
    public ResponseEntity<List<Video>> all() { return ResponseEntity.ok(svc.getAll()); }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Video> create(@RequestBody Video v) { return ResponseEntity.status(HttpStatus.CREATED).body(svc.create(v)); }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Video payload) {
        return svc.getById(id).map(existing -> {
            existing.setTitle(payload.getTitle());
            existing.setDirector(payload.getDirector());
            existing.setGenre(payload.getGenre());
            existing.setAvailable(payload.isAvailable());
            return ResponseEntity.ok(svc.update(existing));
        }).orElseThrow(() -> new ResourceNotFoundException("Video not found with id: " + id));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (svc.getById(id).isPresent()) {
            svc.delete(id);
            return ResponseEntity.ok("Deleted");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Video not found");
    }
}
