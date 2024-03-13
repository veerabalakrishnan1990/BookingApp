package com.trainbookingapp.net.service;

import com.trainbookingapp.net.model.Booking;
import com.trainbookingapp.net.model.Train;
import com.trainbookingapp.net.repository.TrainRepository;

import java.util.Optional;

public class AvailableState implements BookingState {

    private final TrainRepository trainRepository;

    public AvailableState(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }

    @Override
    public void bookTicket(Booking booking) {
        // Move to Booked state
        BookedState bookedState = new BookedState(trainRepository);
        changeState(booking, bookedState);

        System.out.println("Booking ticket for seat: " + booking.getSeatNumber());
    }

    @Override
    public void cancelBooking(Booking booking) {
        // No-operation, as the seat is not booked yet
    }

    private void changeState(Booking booking, BookingState newState) {
        String seatNumber =  booking.getSeatNumber();
        Optional<Train> train = trainRepository.findById(booking.getTrainId());

        // Perform state transition logic
        train.get().getSections().stream()
                .flatMap(section -> section.getSeats().stream())
                .filter(seat -> seatNumber.equals(seat.getSeatNumber()))
                .findFirst()
                .ifPresent(seat -> {
                    seat.setState(newState.getClass().getSimpleName());
                    // Update the corresponding booking state
                    seat.setBooking(booking);
                });
        trainRepository.save(train.get());
    }
}

