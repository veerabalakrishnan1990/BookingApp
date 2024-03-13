package com.trainbookingapp.net.service;

import com.trainbookingapp.net.model.Booking;
import com.trainbookingapp.net.model.Seat;
import com.trainbookingapp.net.model.Section;
import com.trainbookingapp.net.model.Train;
import com.trainbookingapp.net.repository.TrainRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AvailableStateTest {

    @Mock
    private TrainRepository trainRepository;

    @InjectMocks
    private AvailableState availableState;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testBookTicket() {
        Booking booking = new Booking();
        booking.setTrainId("123");
        booking.setSeatNumber("A1");

        Train train = new Train();
        train.setId("123");
        Section section = new Section();
        Seat seat = new Seat();
        seat.setSeatNumber("A1");
        List<Seat> seats = new ArrayList<>();
        seats.add(seat);
        section.setSeats(seats);
        List<Section> sections = new ArrayList<>();
        sections.add(section);
        train.setSections(sections);

        when(trainRepository.findById("123")).thenReturn(Optional.of(train));

        availableState.bookTicket(booking);

        verify(trainRepository).save(any(Train.class));
    }

    @Test
    public void testCancelBooking() {
        // No-operation, as the seat is not booked yet
        Booking booking = new Booking();
        availableState.cancelBooking(booking); // Just for coverage
    }
}
