package com.trainbookingapp.net.service;

import com.trainbookingapp.net.model.Booking;
import com.trainbookingapp.net.model.Seat;
import org.springframework.data.redis.core.RedisTemplate;

public interface BookingState {
    void bookTicket(Booking booking);

    void cancelBooking(Booking booking);
}
