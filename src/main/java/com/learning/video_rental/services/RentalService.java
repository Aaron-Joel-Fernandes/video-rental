package com.learning.video_rental.services;



import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.learning.video_rental.entity.*;
import com.learning.video_rental.repository.*;
import java.time.Instant;
import java.util.List;

@Service
public class RentalService {
    private final RentalRepository rentalRepo;
    private final UserRepository userRepo;
    private final VideoRepository videoRepo;

    public RentalService(RentalRepository rentalRepo, UserRepository userRepo, VideoRepository videoRepo) {
        this.rentalRepo = rentalRepo;
        this.userRepo = userRepo;
        this.videoRepo = videoRepo;
    }

    @Transactional
    public Rental rentVideo(String userEmail, Long videoId) {
        User user = userRepo.findByEmail(userEmail).orElseThrow(() -> new IllegalArgumentException("User not found"));
        List<Rental> active = rentalRepo.findByUserAndReturnedAtIsNull(user);
        if (active.size() >= 2) throw new IllegalStateException("Cannot have more than 2 active rentals");

        Video video = videoRepo.findById(videoId).orElseThrow(() -> new IllegalArgumentException("Video not found"));
        if (!video.isAvailable()) throw new IllegalStateException("Video not available");

        video.setAvailable(false);
        videoRepo.save(video);

        Rental rental = new Rental();
        rental.setUser(user);
        rental.setVideo(video);
        rental.setRentedAt(Instant.now());
        return rentalRepo.save(rental);
    }

    @Transactional
    public Rental returnVideo(String userEmail, Long videoId) {
        User user = userRepo.findByEmail(userEmail).orElseThrow(() -> new IllegalArgumentException("User not found"));
        // find active rental for user and video
        List<Rental> active = rentalRepo.findByUserAndReturnedAtIsNull(user);
        Rental target = active.stream().filter(r -> r.getVideo().getId().equals(videoId)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Active rental not found for this user and video"));

        target.setReturnedAt(Instant.now());
        Video video = target.getVideo();
        video.setAvailable(true);
        videoRepo.save(video);
        Rental rent=rentalRepo.save(target);
        return rent;
    }
}
