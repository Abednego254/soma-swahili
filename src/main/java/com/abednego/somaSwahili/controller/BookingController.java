package com.abednego.somaSwahili.controller;

import com.abednego.somaSwahili.dto.booking.BookingRequestDTO;
import com.abednego.somaSwahili.model.booking.Booking;
import com.abednego.somaSwahili.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> createBooking(@Valid @RequestBody BookingRequestDTO bookingDto) {
        log.info("Creating booking for student ID {} with tutor ID {}", 
                bookingDto.getStudentId(), bookingDto.getTutorId());
        Booking booking = bookingService.bookSession(bookingDto);
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Booking>> getStudentBookings(@PathVariable Long studentId) {
        log.info("Fetching bookings for student ID: {}", studentId);
        List<Booking> bookings = bookingService.getStudentBookings(studentId);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/tutor/{tutorId}")
    public ResponseEntity<List<Booking>> getTutorBookings(@PathVariable Long tutorId) {
        log.info("Fetching bookings for tutor ID: {}", tutorId);
        List<Booking> bookings = bookingService.getTutorBookings(tutorId);
        return ResponseEntity.ok(bookings);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Booking> updateBookingStatus(
            @PathVariable Long id,
            @RequestParam("status") String status) {
        log.info("Updating booking ID {} status to {}", id, status);
        Booking updatedBooking = bookingService.updateBookingStatus(id, status);
        return ResponseEntity.ok(updatedBooking);
    }
}
