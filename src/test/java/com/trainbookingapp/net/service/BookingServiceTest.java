package com.trainbookingapp.net.service;

import com.trainbookingapp.net.model.*;
import com.trainbookingapp.net.repository.TrainRepository;
import com.trainbookingapp.net.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @Mock
    private TrainRepository trainRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RedissonClient redissonClient;

    @InjectMocks
    private BookingService bookingService;

    @Mock
    RLock lock;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testBookTicket_Success() throws InterruptedException {
        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setTrainId("123");
        bookingRequest.setUserId("456");
        bookingRequest.setSeatNumber("A1");
        bookingRequest.setArrivalStation("Station1");
        bookingRequest.setDepartureStation("Station2");

        Train train = new Train();
        train.setId("123");
        List<Section> sections = new ArrayList<>();
        Section section = new Section();
        section.setSectionId("1");
        List<Seat> seats = new ArrayList<>();
        Seat seat = new Seat();
        seat.setSeatNumber("A1");
        seat.setState("AvailableState");
        seats.add(seat);
        section.setSeats(seats);
        sections.add(section);
        train.setSections(sections);

        User user = new User();
        user.setId("456");

        Booking booking = new Booking();
        booking.setId(UUID.randomUUID().toString());
        booking.setTrainId("123");
        booking.setUser("456");
        booking.setSeatNumber("A1");
        booking.setArrivalStation("Station1");
        booking.setDepartureStation("Station2");

        // Configure lock behavior
        when(redissonClient.getLock(anyString())).thenReturn(lock);
        when(lock.tryLock(10, TimeUnit.SECONDS)).thenReturn(true);
        when(lock.isHeldByCurrentThread()).thenReturn(true);

        when(trainRepository.findById("123")).thenReturn(Optional.of(train));
        when(userRepository.findById("456")).thenReturn(Optional.of(user));
        when(lock.tryLock(10, TimeUnit.SECONDS)).thenReturn(false);

        String result = bookingService.bookTicket(bookingRequest);

        assertEquals("Booking request processed for seat: A1", result);
        verify(trainRepository, times(1)).findById("123");
        verify(userRepository, times(1)).findById("456");
        verify(lock, times(1)).tryLock(10, TimeUnit.SECONDS);
    }

    @Test
    public void testBookTicket_SeatAlreadyBooked() {
        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setTrainId("123");
        bookingRequest.setUserId("456");
        bookingRequest.setSeatNumber("A1");
        bookingRequest.setArrivalStation("Station1");
        bookingRequest.setDepartureStation("Station2");

        Train train = new Train();
        train.setId("123");
        List<Section> sections = new ArrayList<>();
        Section section = new Section();
        section.setSectionId("1");
        List<Seat> seats = new ArrayList<>();
        Seat seat = new Seat();
        seat.setSeatNumber("A1");
        seat.setState("BookedState"); // Seat already booked
        seats.add(seat);
        section.setSeats(seats);
        sections.add(section);
        train.setSections(sections);

        when(trainRepository.findById("123")).thenReturn(Optional.of(train));

        String result = bookingService.bookTicket(bookingRequest);

        assertEquals("Seat A1 is already booked by another user.", result);
        verify(trainRepository, times(1)).findById("123");
    }
}
