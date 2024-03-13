package com.trainbookingapp.net.service;

import com.trainbookingapp.net.model.Booking;
import com.trainbookingapp.net.model.Train;
import com.trainbookingapp.net.repository.TrainRepository;

import java.util.Optional;

public class BookedState implements BookingState {
    private final TrainRepository trainRepository;

    public BookedState(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }

    @Override
    public void bookTicket(Booking booking) {
        // No-operation, as the seat is already booked
    }

    @Override
    public void cancelBooking(Booking booking) {
        String seatNumber = booking.getSeatNumber();
        // Move to Available state on cancellation
        AvailableState availableState = new AvailableState(trainRepository);
        changeState(booking, availableState);
        System.out.println("Canceling booking for seat: " + seatNumber);
    }

    private void changeState(Booking booking, BookingState newState) {
        String seatNumber =  booking.getSeatNumber();
        Optional<Train> train = trainRepository.findById(booking.getTrainId());
        // Perform state transition logic
        System.out.println("Changing seat " + seatNumber + " state to " + newState.getClass().getSimpleName());

        // Persist the state in Redis
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
