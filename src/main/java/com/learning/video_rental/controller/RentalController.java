package com.learning.video_rental.controller;


import com.learning.video_rental.entity.Rental;
import com.learning.video_rental.services.RentalService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RentalController {

    private final RentalService rentalService;
    public RentalController(RentalService rentalService) { this.rentalService = rentalService; }

    // rent: POST /videos/{videoId}/rent
    @PostMapping("/videos/{videoId}/rent")
    public ResponseEntity<?> rent(@PathVariable Long videoId, Authentication authentication) {
        String email = authentication.getName();
        try {
            Rental rent = rentalService.rentVideo(email, videoId);
            return ResponseEntity.ok(rent);
        } catch (IllegalStateException | IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    // return: POST /videos/{videoId}/return
    @PostMapping("/videos/{videoId}/return")
    public ResponseEntity<?> returnVideo(@PathVariable Long videoId, Authentication authentication) {
        String email = authentication.getName();
        try {
            Rental  returned = rentalService.returnVideo(email, videoId);
            return ResponseEntity.ok(returned);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}

