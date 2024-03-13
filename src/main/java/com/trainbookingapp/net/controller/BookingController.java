package com.trainbookingapp.net.controller;

import com.trainbookingapp.net.model.BookingInfoRequest;
import com.trainbookingapp.net.model.BookingRequest;
import com.trainbookingapp.net.model.CancelRequest;
import com.trainbookingapp.net.model.Seat;
import com.trainbookingapp.net.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The BookingController class handles the REST API endpoints related to booking operations.
 */
@RestController
@RequestMapping("/api/booking")
public class BookingController {
    private final BookingService bookingService;

    /**
     * Constructor for the BookingController class.
     * @param bookingService The BookingService instance for handling booking operations.
     */
    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    /**
     * Handles the POST request to book a ticket.
     * @param bookingRequest The BookingRequest object representing the booking request.
     * @return A string indicating the status of the booking operation.
     */
    @PostMapping("/book")
    public String bookTicket(@RequestBody BookingRequest bookingRequest) {
        return bookingService.bookTicket(bookingRequest);
    }

    /**
     * Handles the POST request to cancel a booking.
     * @param cancelRequest The CancelRequest object representing the cancel request.
     * @return A string indicating the status of the cancellation operation.
     */
    @PostMapping("/cancel")
    public String cancelBooking(@RequestBody CancelRequest cancelRequest) {
        return bookingService.cancelBooking(cancelRequest);
    }

    /**
     * Handles the GET request to retrieve seat information.
     * @param bookingInfoRequest The BookingInfoRequest object representing the booking info request.
     * @return A list of Seat objects containing seat information.
     */
    @GetMapping("/info")
    public List<Seat> getSeatInfo(@RequestBody BookingInfoRequest bookingInfoRequest) {
        return bookingService.getBookingInfo(bookingInfoRequest);
    }
}

