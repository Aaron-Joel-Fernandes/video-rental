package com.learning.video_rental.dto;

import com.learning.video_rental.entity.Video;

public class VideoResponseDTO {
    private Long id;
    private String title;
    private String director;
    private String genre;
    private boolean available;

    public VideoResponseDTO(Video video) {
        this.id = video.getId();
        this.title = video.getTitle();
        this.director = video.getDirector();
        this.genre = video.getGenre();
        this.available = video.isAvailable();
    }
}