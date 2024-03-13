/**
 * The AvailableState class represents the state of a seat when it is available for booking.
 */
package com.trainbookingapp.net.service;

import com.trainbookingapp.net.model.Booking;
import com.trainbookingapp.net.model.Train;
import com.trainbookingapp.net.repository.TrainRepository;

import java.util.Optional;

/**
 * The AvailableState class represents the state of a seat when it is available for booking.
 */
public class AvailableState implements BookingState {

    private final TrainRepository trainRepository;

    /**
     * Constructor for the AvailableState class.
     * @param trainRepository The TrainRepository instance for accessing train data.
     */
    public AvailableState(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }

    /**
     * Books a ticket for the provided booking.
     * Moves the seat to the Booked state.
     * @param booking The Booking object representing the booking to be made.
     */
    @Override
    public void bookTicket(Booking booking) {
        // Move to Booked state
        BookedState bookedState = new BookedState(trainRepository);
        changeState(booking, bookedState);

        System.out.println("Booking ticket for seat: " + booking.getSeatNumber());
    }

    /**
     * Cancels a booking for the provided booking.
     * No-operation as the seat is not booked yet.
     * @param booking The Booking object representing the booking to be cancelled.
     */
    @Override
    public void cancelBooking(Booking booking) {
        // No-operation, as the seat is not booked yet
    }

    /**
     * Changes the state of the seat to the new state provided.
     * Updates the corresponding booking state.
     * @param booking The Booking object representing the booking.
     * @param newState The new state to change to.
     */
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

