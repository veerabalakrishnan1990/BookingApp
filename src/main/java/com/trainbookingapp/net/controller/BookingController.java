package com.trainbookingapp.net.controller;

import com.trainbookingapp.net.model.BookingInfoRequest;
import com.trainbookingapp.net.model.BookingRequest;
import com.trainbookingapp.net.model.CancelRequest;
import com.trainbookingapp.net.model.Seat;
import com.trainbookingapp.net.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booking")
public class BookingController {
    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/book")
    public String bookTicket(@RequestBody BookingRequest bookingRequest) {
        return bookingService.bookTicket(bookingRequest);
    }

    @PostMapping("/cancel")
    public String cancelBooking(@RequestBody CancelRequest cancelRequest) {
        return bookingService.cancelBooking(cancelRequest);
    }

    @GetMapping("/info")
    public List<Seat> getSeatInfo(@RequestBody BookingInfoRequest bookingInfoRequest) {
        return bookingService.getBookingInfo(bookingInfoRequest);
    }
}

