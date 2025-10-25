package com.learning.video_rental.controller;

import com.learning.video_rental.entity.Video;
import com.learning.video_rental.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/videos")
public class VideoController {

    @Autowired
    private VideoService videoService;

    //  Public/Customer accessible endpoint
    @GetMapping
    public ResponseEntity<List<Video>> getAllVideos() {
        List<Video> videos = videoService.getAllVideos();
        return ResponseEntity.ok(videos);
    }

    //  ADMIN only — create a new video
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Video> createVideo(@RequestBody Video video) {
        Video savedVideo = videoService.createVideo(video);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedVideo);
    }

    // ADMIN only — update a video by ID
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateVideo(@PathVariable Long id, @RequestBody Video updatedVideo) {
        Optional<Video> videoOpt = videoService.getVideoById(id);
        if (videoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Video not found");
        }

        Video existingVideo = videoOpt.get();
        existingVideo.setTitle(updatedVideo.getTitle());
        existingVideo.setDirector(updatedVideo.getDirector());
        existingVideo.setGenre(updatedVideo.getGenre());
        existingVideo.setAvailable(updatedVideo.isAvailable());

        Video savedVideo = videoService.createVideo(existingVideo);
        return ResponseEntity.ok(savedVideo);
    }

    // ADMIN only — delete a video
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVideo(@PathVariable Long id) {
        if (!videoService.deleteVideo(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Video not found");
        }
        return ResponseEntity.ok("Video deleted successfully");
    }

    //CUSTOMER & ADMIN — get a specific video
    @GetMapping("/{id}")
    public ResponseEntity<?> getVideoById(@PathVariable Long id) {
        Optional<Video> video = videoService.getVideoById(id);
        return video.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Video not found"));
    }
}
