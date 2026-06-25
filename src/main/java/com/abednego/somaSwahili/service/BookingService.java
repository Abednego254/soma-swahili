package com.abednego.somaSwahili.service;

import com.abednego.somaSwahili.dto.booking.BookingRequestDTO;
import com.abednego.somaSwahili.model.booking.Booking;

import java.util.List;

public interface BookingService {
    Booking bookSession(BookingRequestDTO dto);
    List<Booking> getStudentBookings(Long studentId);
    List<Booking> getTutorBookings(Long tutorId);
    Booking updateBookingStatus(Long bookingId, String status);
}
